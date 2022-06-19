package andronomos.androtech.block.animalfarmer;

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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AnimalFarmerContainer extends BaseContainerMenu {
	public AnimalFarmerBE blockEntity;

	public AnimalFarmerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.ANIMAL_FARMER.get(), windowId, inventory);

		BlockEntity blockEntityAtPos = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntityAtPos != null && blockEntityAtPos instanceof AnimalFarmerBE animalFarmerBE) {
			blockEntity = animalFarmerBE;

			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addMachineInventory(h);
			});

			addSlot(new SlotItemHandler(blockEntity.shearsSlot, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET, 16));
			addSlot(new SlotItemHandler(blockEntity.bucketSlot, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET, 34));
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotId) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(slotId);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			returnStack = stack.copy();

			int containerEnd = Const.CONTAINER_MACHINE_MEDIUM_SIZE;

			if(slotId <= containerEnd) {
				if (!this.moveItemStackTo(stack, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (stack.getItem() == Items.SHEARS) {
					if (!this.moveItemStackTo(stack, 21, this.slots.size(), false)) {
						return ItemStack.EMPTY;
					}
				} else if (stack.getItem() == Items.BUCKET) {
					if (!this.moveItemStackTo(stack, 22, this.slots.size(), false)) {
						return ItemStack.EMPTY;
					}
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

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.ANIMAL_FARMER.get());
	}
}
