package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.block.machine.cropfarmer.harvesters.*;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachineEnergy;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.BlockPosUtils;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CropFarmerBlockEntity extends MachineBlockEntity implements MenuProvider {
	private final List<IHarvester> harvesters = new ArrayList<>();

	public CropFarmerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CROP_FARMER.get(), pos, state);

		harvesters.add(new NetherWartHarvester());
		harvesters.add(new CropHarvester());
		harvesters.add(new StemGrownBlockHarvester());
		harvesters.add(new SweetBerryHarvester());
		harvesters.add(new SugarcaneHarvester());
	}

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

	@Override
	protected ModEnergyStorage createEnergyHandler() {
		return new ModEnergyStorage(AndroTechConfig.CROP_FARMER_ENERGY_CAPACITY.get(), AndroTechConfig.CROP_FARMER_ENERGY_CAPACITY.get()) {
			@Override
			public void onEnergyChanged() {
				setChanged();
				AndroTechPacketHandler.sendToClients(new SyncMachineEnergy(this.energy, getBlockPos()));
			}
		};
	}

	@Override
	public AABB getWorkArea() {
		return RadiusUtils.nineByOneByNineCubeFromTop(getBlockPos());
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
		if(!state.getValue(CropFarmer.POWERED)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
		if (!state.getValue(CropFarmer.POWERED)) return;
		if (!shouldTick()) return;

		//demo
		blockEntity.energyHandler.receiveEnergy(AndroTechConfig.CROP_FARMER_ENERGY_TRANSFER_RATE.get(), false);


		if(!hasEnoughEnergy(blockEntity)) return;
		extractEnergy(blockEntity);
		setChanged(level, pos, state);

		List<BlockPos> nearbyCrops = getCrops(getWorkArea(), level);

		for (BlockPos nearbyCropPos : nearbyCrops) {
			BlockState cropState = level.getBlockState(nearbyCropPos);
			Block block = cropState.getBlock();

			for (IHarvester harvester : harvesters) {
				boolean harvestSuccessful = harvester.tryHarvest(block, cropState, level, nearbyCropPos, itemHandler);

				if(harvestSuccessful) {
					break;
				}
			}
		}
	}

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
