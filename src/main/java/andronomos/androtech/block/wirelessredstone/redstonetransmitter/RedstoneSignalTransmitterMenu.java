package andronomos.androtech.block.wirelessredstone.redstonetransmitter;

import andronomos.androtech.block.base.BaseMenu;
import andronomos.androtech.inventory.server.RestrictedSlotHandler;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class RedstoneSignalTransmitterMenu extends BaseMenu {
	public RedstoneSignalTransmitterMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public RedstoneSignalTransmitterMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.REDSTONE_SIGNAL_TRANSMITTER_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof RedstoneSignalTransmitterBlockEntity transmitterBlockEntity) {
			transmitterBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
				addSlot(new RestrictedSlotHandler(itemHandler, 0, 80, 30, ItemRegistry.GPS_RECORDER.get().getDefaultInstance(), 1));
			});
		}
		setSlotIndexes(RedstoneSignalTransmitterBlock.SLOTS);
		addDataSlots(data);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, blockEntity.getBlockPos()), this.player, BlockRegistry.REDSTONE_SIGNAL_TRANSMITTER.get());
	}
}
