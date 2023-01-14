package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.cropfarmer.harvesters.*;
import andronomos.androtech.block.machine.MachineTickingBlockEntity;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachineEnergy;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.BlockPosUtils;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CropFarmerBlockEntity extends MachineTickingBlockEntity implements MenuProvider {
	//public ItemStackHandler hoeSlot;
	//private final LazyOptional<IItemHandler> hoeSlotHandler = LazyOptional.of(() -> hoeSlot);
	//public final LazyOptional<IItemHandler> everything = LazyOptional.of(() -> new CombinedInvWrapper(hoeSlot, itemHandler));
	private final List<IHarvester> harvesters = new ArrayList<>();
	private static final int ENERGY_REQUIREMENT = 32;
	private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

	public CropFarmerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CROP_FARMER.get(), pos, state);

		//hoeSlot= new ItemStackHandler() {
		//	@Override
		//	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		//		return stack.getItem() instanceof HoeItem;
		//	}
		//};

		harvesters.add(new NetherWartHarvester());
		harvesters.add(new CropHarvester());
		harvesters.add(new StemGrownBlockHarvester());
		harvesters.add(new SweetBerryHarvester());
		harvesters.add(new SugarcaneHarvester());
	}

	private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
		@Override
		public void onEnergyChanged() {
			setChanged();
			AndroTechPacketHandler.sendToClients(new SyncMachineEnergy(this.energy, getBlockPos()));
		}
	};

	public IEnergyStorage getEnergyStorage() {
		return ENERGY_STORAGE;
	}

	public void setEnergyLevel(int energy) {
		this.ENERGY_STORAGE.setEnergy(energy);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return lazyItemHandler.cast();
		}

		if(cap == ForgeCapabilities.ENERGY) {
			return lazyEnergyHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyEnergyHandler.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("crop_farmer.energy", ENERGY_STORAGE.getEnergyStored());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		ENERGY_STORAGE.setEnergy(tag.getInt("crop_farmer.energy"));
	}








	/////////////////////////////////////////////

	@Override
	public Component getDisplayName() {
		return Component.translatable(CropFarmer.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new CropFarmerMenu(id, inventory, this);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.INVENTORY_MACHINE_SIZE) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slotId, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

	//@NotNull
	//@Override
	//public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
	//	if (cap == ForgeCapabilities.ITEM_HANDLER) {
	//		this.setChanged();
	//
	//		//If block is broken
	//		if(level != null && level.getBlockState(getBlockPos()).getBlock() != getBlockState().getBlock()) {
	//			return everything.cast();
	//		}
	//
	//		if(side == null) {
	//			return lazyItemHandler.cast();
	//		}
	//
	//		if(side == Direction.DOWN) {
	//			return lazyItemHandler.cast();
	//		} else {
	//			return hoeSlotHandler.cast();
	//		}
	//	}
	//
	//	return super.getCapability(cap, side);
	//}

	@Override
	public AABB getWorkArea() {
		return RadiusUtils.nineByOneByNineCubeFromTop(getBlockPos());
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(!state.getValue(CropFarmer.POWERED)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, CropFarmerBlockEntity blockEntity) {
		if(state.getValue(CropFarmer.POWERED)) {
			if(!shouldTick()) return;
			//ItemStack hoe = hoeSlot.getStackInSlot(0);
			//if(hoe.isEmpty()) return;

			//demo
			blockEntity.ENERGY_STORAGE.receiveEnergy(64, false);
			
			if(!hasEnoughEnergy(blockEntity)) return;

			extractEnergy(blockEntity);
			setChanged(level, pos, state);


			FakePlayer fakePlayer = FakePlayerFactory.get(level, AndroTech.PROFILE);

			List<BlockPos> nearbyCrops = getCrops(getWorkArea(), level);

			for (BlockPos nearbyCropPos : nearbyCrops) {
				BlockState cropState = level.getBlockState(nearbyCropPos);
				Block block = cropState.getBlock();

				for (IHarvester harvester : harvesters) {
					boolean harvestSuccessful = harvester.tryHarvest(block, cropState, level, nearbyCropPos, itemHandler);

					//if(harvestSuccessful) {
					//	ItemStackUtils.applyDamage(fakePlayer, hoe, 1, false);
					//	break;
					//}
				}
			}
		}
	}

	private boolean hasEnoughEnergy(CropFarmerBlockEntity blockEntity) {
		return blockEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQUIREMENT;
	}

	private void extractEnergy(CropFarmerBlockEntity blockEntity) {
		blockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQUIREMENT, false);
	}



	//@Override
	//public void saveAdditional(CompoundTag tag) {
	//	super.saveAdditional(tag);
	//	tag.put("Hoe", hoeSlot.serializeNBT());
	//}
	//
	//@Override
	//public void load(CompoundTag tag) {
	//	super.load(tag);
	//	if (tag.contains("Hoe")) {
	//		hoeSlot.deserializeNBT(tag.getCompound("Hoe"));
	//	}
	//}

	//@Override
	//public void setRemoved() {
	//	super.setRemoved();
	//	hoeSlotHandler.invalidate();
	//}

	public static List<BlockPos> getCrops(AABB scanArea, Level level) {
		List<BlockPos> crops = new ArrayList<>();
		List<BlockPos> blocksInArea = BlockPosUtils.getBlockPosInAABB(scanArea);

		for (BlockPos nearbyPos : blocksInArea) {
			BlockState nearbyState = level.getBlockState(nearbyPos);

			if(nearbyState == Blocks.AIR.defaultBlockState()) continue;

			Block nearbyBlock = nearbyState.getBlock();

			if (nearbyBlock instanceof IPlantable || nearbyBlock instanceof StemGrownBlock) {
				crops.add(nearbyPos);
			}

			if(nearbyBlock instanceof StemGrownBlock) {
				crops.add(nearbyPos);
			}
		}

		return crops;
	}


}
