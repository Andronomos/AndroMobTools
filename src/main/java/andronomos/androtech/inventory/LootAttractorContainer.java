package andronomos.androtech.inventory;

import andronomos.androtech.Const;
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
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class LootAttractorContainer extends BaseContainerMenu {
	public final BlockEntity blockEntity;

	public LootAttractorContainer(int windowId, BlockPos pos, Inventory inventory, Player player) {
		super(ModContainers.LOOT_ATTRACTOR.get(), windowId);

		blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		if(blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for (int i = 0; i < 6; i++) {
					for(int j = 0; j < 9; j++) {
						addSlot(new SlotItemHandler(h, j + i * 9, Const.CONTAINER_SLOT_X_OFFSET + j * Const.SCREEN_SLOT_SIZE, Const.SCREEN_SLOT_SIZE + i * Const.SCREEN_SLOT_SIZE));
					}
				}
			});
		}

		layoutPlayerInventorySlots(8, 140);
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, ModBlocks.ATTRACTOR.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotIndex);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			returnStack = stack.copy();

			//if we're pulling an item from the container
			if(slotIndex < 54) {
				//attempt to the move the stack to the player's inventory or hotbar
				if (!moveItemStackTo(stack, 54, 90, false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotIndex < 90 && !moveItemStackTo(stack, 0, 54, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerEntity, stack);
		}

		return returnStack;
	}


}
