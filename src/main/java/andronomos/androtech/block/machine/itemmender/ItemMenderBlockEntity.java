package andronomos.androtech.block.machine.itemmender;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.block.machine.cropfarmer.CropFarmer;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachineEnergy;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemMenderBlockEntity extends MachineBlockEntity implements MenuProvider {
	public ItemMenderBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ITEM_MENDER.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.INVENTORY_VANILLA_LARGE_SIZE) {
			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return ItemStackUtils.isRepairable(stack);
			}
		};
	}

	@Override
	protected ModEnergyStorage createEnergyHandler() {
		return new ModEnergyStorage(AndroTechConfig.ITEM_MENDER_ENERGY_CAPACITY.get(), AndroTechConfig.ITEM_MENDER_ENERGY_TRANSFER_RATE.get()) {
			@Override
			public void onEnergyChanged() {
				setChanged();
				AndroTechPacketHandler.sendToClients(new SyncMachineEnergy(this.energy, getBlockPos()));
			}
		};
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
		if(!state.getValue(CropFarmer.POWERED)) return;
		if(!shouldTick()) return;

		//debug
		AndroTech.LOGGER.info("ItemMenderBlockEntity#serverTick | energyHandler.getEnergyStored >> {}", energyHandler.getEnergyStored());
		return;


		//if(!hasEnoughEnergy(blockEntity)) return;
		//extractEnergy(blockEntity);
		//setChanged(level, pos, state);
		//
		//getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
		//	for (int i = 0; i < itemHandler.getSlots(); i++) {
		//		ItemStack itemstack = itemHandler.getStackInSlot(i);
		//		if (!ItemStackUtils.isRepairable(itemstack)) continue;
		//		itemstack.setDamageValue(itemstack.getDamageValue() - AndroTechConfig.MENDING_MODULE_REPAIR_RATE.get());
		//	}
		//});
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(ItemMender.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new ItemMenderMenu(pContainerId, pPlayerInventory, this);
	}
}
