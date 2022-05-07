package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.block.entity.RedstoneTransmitterBE;
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

public class MobClonerContainer extends BaseContainerMenu {
	public BlockEntity blockEntity;

	public MobClonerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.MOB_CLONER.get(), windowId, inventory);

		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for(int s = 0; s < MobClonerBE.CLONER_SLOTS; s++) {
					addSlot(new SlotItemHandler(h, s, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * s, 27));
				}
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

			int containerEnd = MobClonerBE.CLONER_SLOTS;

			if(index <= containerEnd) {
				if (!this.moveItemStackTo(stack, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (stack.getItem() == ModItems.MOB_CLONING_MODULE.get()) {
					//if(ItemStackUtil.containsEntity(stack)) {
						if (!this.moveItemStackTo(stack, 0, MobClonerBE.CLONER_SLOTS, false)) {
							return ItemStack.EMPTY;
						}
					//}
				} else if (!this.moveItemStackTo(stack, 0, containerEnd, false)) {
					return ItemStack.EMPTY;
				}
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
