package andronomos.androtech.block.redstonetransmitter;

import andronomos.androtech.Const;
import andronomos.androtech.block.BaseContainerMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RedstoneTransmitterContainer extends BaseContainerMenu {
	public BlockEntity blockEntity;

	public RedstoneTransmitterContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.REDSTONE_TRANSMITTER.get(), windowId, inventory);

		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for(int s = 0; s < RedstoneTransmitterBE.TRANSMITTER_SLOTS; s++) {
					addSlot(new SlotItemHandler(h, s, Const.CONTAINER_GENERIC_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * s, 27));
				}
			});
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.REDSTONE_TRANSMITTER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player playerEntity, int slotId) {
		final Slot slot = this.slots.get(slotId);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();

			int containerEnd = RedstoneTransmitterBE.TRANSMITTER_SLOTS;

			if(slotId <= containerEnd) {
				if (!this.moveItemStackTo(stack, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if(stack.getItem() == ModItems.BLOCK_GPS_RECORDER.get()) {
					if(ItemStackUtil.getBlockPos(stack) != null) {
						if(!this.moveItemStackTo(stack, 0, containerEnd, false)) {
							return ItemStack.EMPTY;
						}
					}
				}
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return ItemStack.EMPTY;
	}
}
