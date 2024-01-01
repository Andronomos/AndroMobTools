package andronomos.androtech.block.base;

import andronomos.androtech.Constants;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMenu extends AbstractContainerMenu {
	private final ContainerData data;

	public final BlockEntity blockEntity;
	protected final Level level;
	protected final Inventory inventory;
	protected final Player player;
	protected static int playerInventoryFirstSlot;
	protected static int playerInventoryLastSlot;
	protected static int blockEntityFirstSlot;
	protected static int blockEntityLastSlot;

	public BaseMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(menuType, containerId);
		checkContainerSize(inventory, inventory.getContainerSize());
		blockEntity = entity;
		this.level = inventory.player.level();
		this.data = data;
		this.inventory = inventory;
		this.player = inventory.player;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot sourceSlot = slots.get(slotIndex);
		if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackCopy = sourceStack.copy();

		if(slotIndex >= playerInventoryFirstSlot) {
			if(!moveItemStackTo(sourceStack, blockEntityFirstSlot, blockEntityLastSlot, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			if(!moveItemStackTo(sourceStack, playerInventoryFirstSlot, playerInventoryLastSlot, false)) {
				return ItemStack.EMPTY;
			}
		}

		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(player, sourceStack);
		return sourceStackCopy;
	}

	protected void setSlotIndexes(int slotCount) {
		blockEntityFirstSlot = 0;
		blockEntityLastSlot = Math.max(slotCount, 1); //moveItemStackTo requires 2 different slotIds to work properly so the last slot must be larger than the first
		playerInventoryFirstSlot = slotCount > 1 ? slotCount + 1 : slotCount;
		playerInventoryLastSlot = slotCount > 1 ? slotCount + Constants.PLAYER_INVENTORY_SLOT_COUNT : Constants.PLAYER_INVENTORY_SLOT_COUNT;
	}

	protected void addPlayerInventory() {
		addPlayerInventory(Constants.PLAYER_INVENTORY_MENU_Y_OFFSET);
	}

	protected void addPlayerInventory(int yOffset) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, Constants.MENU_SLOT_X_OFFSET + j * Constants.SCREEN_SLOT_SIZE, yOffset + i * 18));
			}
		}
	}

	protected void addPlayerHotbar() {
		addPlayerHotbar(Constants.PLAYER_HOTBAR_MENU_Y_OFFSET);
	}

	protected void addPlayerHotbar(int yOffset) {
		for (int i = 0; i < 9; ++i) {
			addSlot(new Slot(inventory, i, Constants.MENU_SLOT_X_OFFSET + i * Constants.SCREEN_SLOT_SIZE, yOffset));
		}
	}

	protected void addInventory(IItemHandler handler) {
		for (int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Constants.MENU_SLOT_X_OFFSET + x * Constants.SCREEN_SLOT_SIZE,
						Constants.SCREEN_SLOT_SIZE + y * Constants.SCREEN_SLOT_SIZE));
			}
		}
	}

	protected void addLargeInventory(IItemHandler handler) {
		for (int y = 0; y < 6; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Constants.MENU_SLOT_X_OFFSET + x * Constants.SCREEN_SLOT_SIZE,
						Constants.SCREEN_SLOT_SIZE + y * Constants.SCREEN_SLOT_SIZE));
			}
		}
	}
}
