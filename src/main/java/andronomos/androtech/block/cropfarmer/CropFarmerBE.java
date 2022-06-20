package andronomos.androtech.block.cropfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.TickingMachineBlockEntity;
import andronomos.androtech.block.cropfarmer.harvesters.*;
import andronomos.androtech.block.mobcloner.MobCloner;
import andronomos.androtech.inventory.MachineSlotItemStackHandler;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CropFarmerBE extends TickingMachineBlockEntity {
	public ItemStackHandler hoeSlot;

	private final LazyOptional<IItemHandler> hoeSlotHandler = LazyOptional.of(() -> hoeSlot);
	public final LazyOptional<IItemHandler> everything = LazyOptional.of(() -> new CombinedInvWrapper(hoeSlot, inventoryItems));

	private final List<IHarvester> harvesters = new ArrayList<>();

	public CropFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CROP_FARMER.get(), pos, state);

		hoeSlot = new MachineSlotItemStackHandler(Items.DIAMOND_HOE);

		harvesters.add(new NetherWartHarvester());
		harvesters.add(new CropHarvester());
		harvesters.add(new StemGrownBlockHarvester());
		harvesters.add(new SweetBerryHarvester());
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return new AABB(x - 4, y + 1, z - 4, x + 4, y + 1, z + 4);
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

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!state.getValue(MobCloner.POWERED)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(state.getValue(MobCloner.POWERED)) {
			if(!shouldTick()) return;

			ItemStack hoe = hoeSlot.getStackInSlot(0);

			if(hoe.isEmpty()) return;

			AABB workArea = getWorkArea(pos);

			List<BlockPos> nearbyCrops = BlockUtil.getCropsInArea(workArea, level);

			for (BlockPos nearbyCropPos : nearbyCrops) {
				BlockState cropState = level.getBlockState(nearbyCropPos);
				Block block = cropState.getBlock();

				for (IHarvester harvester : harvesters) {
					boolean harvestSuccessful = harvester.tryHarvest(block, cropState, level, nearbyCropPos, inventoryItems);

					if(harvestSuccessful) {
						break;
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

			if(side == Direction.DOWN) {
				return inventoryHandler.cast();
			} else {
				return hoeSlotHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Hoe", hoeSlot.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Hoe")) {
			hoeSlot.deserializeNBT(tag.getCompound("Hoe"));
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		hoeSlotHandler.invalidate();
	}
}
