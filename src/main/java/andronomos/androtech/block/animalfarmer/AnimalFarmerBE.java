package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.inventory.MachineSlotItemStackHandler;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.InventoryUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class AnimalFarmerBE extends TickingBE {
	public ItemStackHandler shearsSlot;
	public ItemStackHandler bucketSlot;

	private final LazyOptional<IItemHandler> shearsSlotHandler = LazyOptional.of(() -> shearsSlot);
	private final LazyOptional<IItemHandler> bucketSlotHandler = LazyOptional.of(() -> bucketSlot);
	public final LazyOptional<IItemHandler> everything = LazyOptional.of(() -> new CombinedInvWrapper(shearsSlot, bucketSlot, inventoryItems));

	public AnimalFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ANIMAL_FARMER.get(), pos, state);
		shearsSlot = new MachineSlotItemStackHandler(Items.SHEARS);
		bucketSlot = new MachineSlotItemStackHandler(Items.BUCKET);
		tickDelay = Const.TicksInMinutes.ONE;
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_MACHINE_MEDIUM_SIZE) {
			@Override
			public int getSlotLimit(int slotId) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slotId, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		//minX minY minZ maxX maxY maxZ
		return new AABB(x + 4, y, z - 1, x - 4, y + 1, z - 9);
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		//if(!state.getValue(AnimalFarmer.POWERED)) return;
		//
		//double d0 = (double)pos.getX() + level.random.nextDouble();
		//double d1 = (double)pos.getY() + level.random.nextDouble();
		//double d2 = (double)pos.getZ() + level.random.nextDouble();
		//level.addParticle(ParticleTypes., d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(state.getValue(AnimalFarmer.POWERED)) {
			if(!shouldTick()) return;

			if(InventoryUtil.inventoryIsFull(this.inventoryItems)) return;

			AABB workArea = getWorkArea(pos);

			List<Animal> animals = getLevel().getEntitiesOfClass(Animal.class, workArea, EntitySelector.ENTITY_STILL_ALIVE);

			for (Animal animal : animals) {
				FakePlayer fakePlayer = FakePlayerFactory.get(level, AndroTech.PROFILE);

				if(animal instanceof IForgeShearable shearable) {
					ItemStack shears = shearsSlot.getStackInSlot(0);

					if(shears.isEmpty()) continue;

					if(shearable.isShearable(shears, level, animal.blockPosition())) {
						List<ItemStack> items = shearable.onSheared(fakePlayer, shears, level, animal.blockPosition(), 0);

						items.forEach(stack -> {
							ItemStack returnStack = InventoryUtil.insertIntoInventory(stack, inventoryItems);

							if(!returnStack.isEmpty()) {
								ItemStackUtil.drop(level, animal.blockPosition(), returnStack);
							}
						});
					}
				}

				if(animal instanceof Cow cow) {
					ItemStack bucket = bucketSlot.getStackInSlot(0);

					if(bucket.isEmpty()) continue;

					fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
					if (cow.mobInteract(fakePlayer, InteractionHand.MAIN_HAND).consumesAction()) {
						ItemStack stack = fakePlayer.getItemInHand(InteractionHand.MAIN_HAND);
						ItemStack returnStack = InventoryUtil.insertIntoInventory(stack, inventoryItems);

						if(!returnStack.isEmpty()) {
							ItemStackUtil.drop(level, animal.blockPosition(), returnStack);
						}

						bucket.shrink(1);
					}
				}
			}
		}
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			this.setChanged();

			//If block is broken
			if(level != null && level.getBlockState(getBlockPos()).getBlock() != getBlockState().getBlock()) {
				return everything.cast();
			}

			if(side == null) {
				return inventoryHandler.cast();
			}

			if(side == Direction.UP) {
				return shearsSlotHandler.cast();
			}
			if(side == Direction.WEST || side == Direction.EAST || side == Direction.NORTH || side == Direction.SOUTH) {
				return bucketSlotHandler.cast();
			}
			if(side == Direction.DOWN) {
				return inventoryHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Shears", shearsSlot.serializeNBT());
		tag.put("Buckets", bucketSlot.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Shears")) {
			shearsSlot.deserializeNBT(tag.getCompound("Shears"));
		}
		if (tag.contains("Buckets")) {
			bucketSlot.deserializeNBT(tag.getCompound("Buckets"));
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		shearsSlotHandler.invalidate();
		bucketSlotHandler.invalidate();
	}
}
