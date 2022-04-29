package andronomos.androtech.inventory;

import andronomos.androtech.AndroTech;
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

public class CropHarvesterContainer extends BaseContainerMenu {
	public final BlockEntity blockEntity;

	public CropHarvesterContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.CROP_HARVESTER.get(), windowId, inventory);

		this.inventory = inventory;
		this.player = inventory.player;
		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for (int i = 0; i < 6; i++) {
					for(int j = 0; j < 7; j++) {
						addSlot(new SlotItemHandler(h, j + i * 9, Const.CONTAINER_SLOT_X_OFFSET + j * Const.SCREEN_SLOT_SIZE, Const.SCREEN_SLOT_SIZE + i * Const.SCREEN_SLOT_SIZE));
					}
				}
			});
		}

		layoutPlayerInventorySlots(8, 140);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.CROP_HARVESTER.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (index < 54) {
				if (!this.moveItemStackTo(itemstack1, 54, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 54, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}
}
