package andronomos.androtech.block.machine.itemmender;

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

public class ItemMenderMenu extends MachineMenu {
	public ItemMenderBlockEntity blockEntity;

	public ItemMenderMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public ItemMenderMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.ITEM_MENDER.get(), id, inv);

		if(entity != null && entity instanceof ItemMenderBlockEntity itemMenderBlockEntity) {
			this.blockEntity = itemMenderBlockEntity;
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addLargeInventory(h);
			});
		}

		setupSlotIndexs(Const.INVENTORY_VANILLA_LARGE_SIZE);
		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, 140);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.ITEM_MENDER.get());
	}
}
