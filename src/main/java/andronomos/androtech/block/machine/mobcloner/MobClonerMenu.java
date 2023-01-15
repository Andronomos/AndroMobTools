package andronomos.androtech.block.machine.mobcloner;

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

public class MobClonerMenu extends MachineMenu {
	public MobClonerBlockEntity blockEntity;

	public MobClonerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public MobClonerMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.MOB_CLONER.get(), id, inv);

		if (entity != null && entity instanceof MobClonerBlockEntity mobClonerBE) {
			blockEntity = mobClonerBE;
			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, Const.MENU_SLOT_X_CENTER, 33));
			});
		}

		setupSlotIndexs(MobClonerBlockEntity.SLOTS);
		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.MOB_CLONER.get());
	}
}
