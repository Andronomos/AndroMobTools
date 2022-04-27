package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

//public class BackpackContainer extends BaseContainerMenu{
//	public final ItemEntity itemEntity;
//
//	public BackpackContainer(int windowId, Inventory inventory) {
//		super(ModContainers.BACKPACK.get(), windowId, inventory);
//
//		itemEntity = player.getCommandSenderWorld().(pos);
//
//		if(itemEntity != null) {
//			itemEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
//				for (int i = 0; i < 6; i++) {
//					for(int j = 0; j < 9; j++) {
//						addSlot(new SlotItemHandler(h, j + i * 9, Const.CONTAINER_SLOT_X_OFFSET + j * Const.SCREEN_SLOT_SIZE, Const.SCREEN_SLOT_SIZE + i * Const.SCREEN_SLOT_SIZE));
//					}
//				}
//			});
//		}
//
//		layoutPlayerInventorySlots(8, 140);
//	}
//
//	@Override
//	public boolean stillValid(Player p_38874_) {
//		return false;
//	}
//}
