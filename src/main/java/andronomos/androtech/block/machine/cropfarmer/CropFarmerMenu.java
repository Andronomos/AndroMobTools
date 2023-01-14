package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class CropFarmerMenu extends MachineMenu {
	public CropFarmerBlockEntity blockEntity;

	public CropFarmerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public CropFarmerMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.CROP_FARMER.get(), id, inv);
		this.player = inv.player;

		if(entity != null && entity instanceof CropFarmerBlockEntity cropFarmerBlockEntity) {
			blockEntity = cropFarmerBlockEntity;

			blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addMediumMachineInventory(h);
			});

			//addSlot(new SlotItemHandler(blockEntity.hoeSlot, 0, Const.MENU_SLOT_X_OFFSET, 16));
		}

		setupSlotIndexs(Const.INVENTORY_MACHINE_MEDIUM_SIZE);

		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotId) {
		Slot sourceSlot = slots.get(slotId);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceStack.getItem() instanceof HoeItem) {
			if (!this.moveItemStackTo(sourceStack, blockEntityLastSlot, playerInventoryLastSlot, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			if(slotId >= playerInventoryFirstSlot) {
				if(!moveItemStackTo(sourceStack, blockEntityFirstSlot, blockEntityLastSlot, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				if(!moveItemStackTo(sourceStack, playerInventoryFirstSlot, playerInventoryLastSlot, false)) {
					return ItemStack.EMPTY;
				}
			}
		}

		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.CROP_FARMER.get());
	}

	//@Override
	//public void clicked(int slotId, int mouseButton, ClickType clickType, Player player) {
	//	if (mouseButton == 1) { //right click
	//		LevelUtils.displayWorkArea(blockEntity.getWorkArea(), blockEntity.getLevel(), Blocks.AIR);
	//	} else if(mouseButton == 2) { //mouse wheel
	//		LevelUtils.displayWorkArea(blockEntity.getWorkArea(), blockEntity.getLevel(), ModBlocks.OVERLAY.get());
	//	}
	//
	//	super.clicked(slotId, mouseButton, clickType, player);
	//}
}
