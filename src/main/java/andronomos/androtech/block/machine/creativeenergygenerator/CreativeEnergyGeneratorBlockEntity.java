package andronomos.androtech.block.machine.creativeenergygenerator;

import andronomos.androtech.AndroTech;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachineEnergy;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyGeneratorBlockEntity extends MachineBlockEntity implements MenuProvider {
	public CreativeEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CREATIVE_ENERGY_GENERATOR.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return null;
	}

	@Override
	protected ModEnergyStorage createEnergyHandler() {
		return new ModEnergyStorage(AndroTechConfig.CREATIVE_ENERGY_GENERATOR_ENERGY_CAPACITY.get(), AndroTechConfig.CREATIVE_ENERGY_GENERATOR_ENERGY_TRANSFER_RATE.get()) {
			@Override
			public void onEnergyChanged() {
				setChanged();
				AndroTechPacketHandler.sendToClients(new SyncMachineEnergy(this.energy, getBlockPos()));
			}
		};
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
		super.serverTick(level, pos, state, blockEntity);
		setEnergyLevel(AndroTechConfig.CREATIVE_ENERGY_GENERATOR_ENERGY_CAPACITY.get());

		for(Direction direction : Direction.values()) {
			BlockEntity blockEntityAtPos = level.getBlockEntity(worldPosition.relative(direction));

			if(blockEntityAtPos == null) {
				 continue;
			}

			if(blockEntityAtPos instanceof MachineBlockEntity machineBlockEntity) {
				machineBlockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyHandler -> {
					//AndroTech.LOGGER.info("CreativeEnergyGeneratorBlockEntity#serverTick | energyHandler.getEnergyStored >> {}", energyHandler.getEnergyStored());

					if(energyHandler.getEnergyStored() < energyHandler.getMaxEnergyStored()) {
						int toSend = energyHandler.receiveEnergy(AndroTechConfig.CREATIVE_ENERGY_GENERATOR_ENERGY_TRANSFER_RATE.get(), true);

						//AndroTech.LOGGER.info("CreativeEnergyGeneratorBlockEntity#serverTick | toSend >> {}", toSend);

						if(toSend > 0) {
							energyHandler.receiveEnergy(toSend, false);
						}
					}
				});
			}
		}
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(CreativeEnergyGenerator.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new CreativeEnergyGeneratorMenu(pContainerId, pPlayerInventory, this);
	}
}
