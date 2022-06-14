package andronomos.androtech.block.itemmender;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.BaseContainerMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemMenderContainer extends BaseContainerMenu {
	public final BlockEntity blockEntity;

	public ItemMenderContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.ITEM_MENDER.get(), windowId, inventory);

		blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addLargeInventory(h);
			});
		}

		layoutPlayerInventorySlots(8, 140);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.ITEM_MENDER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotId) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotId);

		int containerEnd = Const.CONTAINER_GENERIC_LARGE_SIZE;

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			returnStack = stack.copy();

			if(slotId < containerEnd) {
				if (!this.moveItemStackTo(stack, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, 0, containerEnd, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return returnStack;
	}
}
