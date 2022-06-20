package andronomos.androtech.block.amethystharvester;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AmethystHarvesterContainer extends BaseContainerMenu {
	public AmethystHarvesterBE blockEntity;

	public AmethystHarvesterContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.AMETHYST_HARVESTER.get(), windowId, inventory);

		BlockEntity blockEntityAtPos = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntityAtPos != null && blockEntityAtPos instanceof AmethystHarvesterBE amethystHarvester) {
			blockEntity = amethystHarvester;

			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addMachineInventory(h);
			});

			addSlot(new SlotItemHandler(blockEntity.pickaxeSlot, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET, 16));
		}

		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
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
				if (stack.getItem() instanceof PickaxeItem) {
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
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.AMETHYST_HARVESTER.get());
	}
}
