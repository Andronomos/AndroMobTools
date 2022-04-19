package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.NBTUtil;
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

public class RedstoneTransmitterContainer extends BaseContainerMenu {
	public BlockEntity blockEntity;

	public RedstoneTransmitterContainer(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
		super(ModContainers.REDSTONE_TRANSMITTER.get(), windowId);
		blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
		playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for(int s = 0; s < 9; s++) {
					addSlot(new SlotItemHandler(h, s, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * s, 27));
				}
			});
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, ModBlocks.REDSTONE_TRANSMITTER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player playerEntity, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			returnStack = stack.copy();

			//if we're pulling out from a receiver_card slot (slot 0 through 9)
			if(index <= 9) {
				if(!this.moveItemStackTo(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, returnStack);
			} else {
				if(stack.getItem() == ModItems.REDSTONE_RECEIVER_CARD.get()) {
					if(NBTUtil.getItemStackBlockPos(stack) != null) {
						if(!this.moveItemStackTo(stack, 0, 9, false)) {
							return ItemStack.EMPTY;
						}
					}
				} else if (index < 36) {
					//we must be in the player's inventory, attempt to move the stack to the hotbar
					if (!this.moveItemStackTo(stack, 36, 44, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 44 && !this.moveItemStackTo(stack, 1, 35, false)) {
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

			slot.onTake(playerEntity, stack);
		}

		return ItemStack.EMPTY;
	}
}
