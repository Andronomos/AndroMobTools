package andronomos.androtech.block.cropfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.inventory.BaseContainerMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CropFarmerContainer extends BaseContainerMenu {
	public CropFarmerBE blockEntity;

	public CropFarmerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.CROP_FARMER.get(), windowId, inventory);

		BlockEntity blockEntityAtPos = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntityAtPos != null && blockEntityAtPos instanceof CropFarmerBE cropFarmerBE) {
			blockEntity = cropFarmerBE;

			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addMachineInventory(h);
			});

			addSlot(new SlotItemHandler(blockEntity.hoeSlot, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET, 16));
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.CROP_FARMER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotId) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotId);

		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();

			int containerEnd = Const.CONTAINER_MACHINE_MEDIUM_SIZE;

			if(slotId <= containerEnd) {
				if (!this.moveItemStackTo(stack, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (stack.getItem() instanceof HoeItem) {
					if (!this.moveItemStackTo(stack, 21, this.slots.size(), false)) {
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

		return itemstack;
	}

	@Override
	public void clicked(int slotId, int mouseButton, ClickType clickType, Player player) {
		AndroTech.LOGGER.info("CropFarmerContainer#clicked | slotId: {}", slotId);
		super.clicked(slotId, mouseButton, clickType, player);
	}
}
