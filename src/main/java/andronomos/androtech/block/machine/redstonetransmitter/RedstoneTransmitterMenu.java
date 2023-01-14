package andronomos.androtech.block.machine.redstonetransmitter;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class RedstoneTransmitterMenu extends MachineMenu {
	public BlockEntity blockEntity;

	public RedstoneTransmitterMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public RedstoneTransmitterMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.REDSTONE_TRANSMITTER.get(), id, inv);

		if(entity != null && entity instanceof RedstoneTransmitterBlockEntity redstoneTransmitterBlockEntity) {
			blockEntity = redstoneTransmitterBlockEntity;
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				for(int s = 0; s < RedstoneTransmitterBlockEntity.SLOTS; s++) {
					addSlot(new SlotItemHandler(h, s, Const.MENU_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * s, 27));
				}
			});
		}

		setupSlotIndexs(RedstoneTransmitterBlockEntity.SLOTS);
		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.REDSTONE_TRANSMITTER.get());
	}
}
