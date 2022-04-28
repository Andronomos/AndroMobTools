package andronomos.androtech.inventory;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import andronomos.androtech.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MobClonerContainer extends BaseContainerMenu {
	private BlockEntity blockEntity;

	public MobClonerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.MOB_CLONER.get(), windowId, inventory);

		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 80, 30));
			});
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.MOB_CLONER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			returnStack = stack.copy();

			//if we're pulling out from the ECD slot (slot 0)
			if (index == 0) {
				//attempt to the move the stack to any slot between slot 1 and slot 36
				if (!this.moveItemStackTo(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (stack.getItem() == ModItems.MOB_TRANSPORTER_MODULE.get()) {
					//attempt to move the stack to slot 0.
					if (!this.moveItemStackTo(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 28) {
					//attempt to move the stack to any slot between slot 28 and 36
					if (!this.moveItemStackTo(stack, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(this.player, stack);
		}

		return returnStack;
	}
}
