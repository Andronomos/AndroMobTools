package andronomos.androtech.block.pad.mobkillingpad;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class MobKillingPadMenu extends MachineMenu {
	public MobKillingPadBlockEntity blockEntity;

	public MobKillingPadMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public MobKillingPadMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.MOB_KILLING_PAD.get(), id, inv);

		if(entity != null && entity instanceof MobKillingPadBlockEntity mobKillingPad) {
			blockEntity = mobKillingPad;
			mobKillingPad.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
				addSlot(new SlotItemHandler(itemHandler, 0, Const.MENU_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 30));
			});
		}

		setupSlotIndexs(MobKillingPadBlockEntity.PAD_SLOTS);
		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
	}


    @Override
    public boolean stillValid(Player playerIn) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.MOB_KILLING_PAD.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
		Slot sourceSlot = slots.get(slotId);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if(slotId >= playerInventoryFirstSlot && sourceStack.getItem() instanceof SwordItem) {
			if (!this.moveItemStackTo(sourceStack, blockEntityFirstSlot, blockEntityLastSlot, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			if(!moveItemStackTo(sourceStack, playerInventoryFirstSlot, playerInventoryLastSlot, false)) {
				return ItemStack.EMPTY;
			}
		}

		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
    }
}
