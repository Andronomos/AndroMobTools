package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.InventoryUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class AnimalFarmerBE extends TickingBE {
	public AnimalFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ANIMAL_FARMER.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(AnimalFarmer.SLOTS) {
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
		return new AABB(x - 4, y, z - 4, x + 4, y + 2, z + 4);
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
					ItemStack shears = inventoryItems.getStackInSlot(AnimalFarmer.SHEARS_SLOT);

					if(shears == null) continue;

					if(shearable.isShearable(shears, level, animal.blockPosition())) {
						List<ItemStack> items = shearable.onSheared(fakePlayer, shears, level, animal.blockPosition(), 0);

						items.forEach(stack -> {
							ItemStack returnStack = InventoryUtil.insertIntoInventory(stack, inventoryHandler);

							if(!returnStack.isEmpty()) {
								ItemStackUtil.drop(level, animal.blockPosition(), returnStack);
							}
						});
					}
				}

				if(animal instanceof Cow) {
					//todo: milk it
				}
			}
		}
	}
}
