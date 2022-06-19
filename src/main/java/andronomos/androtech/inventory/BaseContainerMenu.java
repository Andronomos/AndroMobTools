package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseContainerMenu extends AbstractContainerMenu {
	protected Player player;
	protected Inventory inventory;

	protected BaseContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventoryIn) {
		super(type, id);
		this.inventory = inventoryIn;
		this.player = this.inventory.player;
	}

	public void addLargeInventory(IItemHandler handler) {
		for (int y = 0; y < 6; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Const.CONTAINER_GENERIC_SLOT_X_OFFSET + x * Const.SCREEN_SLOT_SIZE,
						Const.SCREEN_SLOT_SIZE + y * Const.SCREEN_SLOT_SIZE));
			}
		}
	}

	public void addMachineInventory(IItemHandler handler) {
		for(int y = 0; y < 3; y++) {
			for (int x = 0; x < 7; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 7,
						Const.CONTAINER_MACHINE_SLOT_X_OFFSET + x * Const.SCREEN_SLOT_SIZE,
						Const.CONTAINER_SLOT_SIZE + y * Const.SCREEN_SLOT_SIZE));
			}
		}
	}

	public void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Player inventory
		addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
	}

	private int addSlotRange(Inventory inventory, int index, int x, int y, int amount, int dx) {
		for (int i = 0 ; i < amount ; i++) {
			addSlot(new Slot(inventory, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	private int addSlotBox(Inventory inventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for (int j = 0 ; j < verAmount ; j++) {
			index = addSlotRange(inventory, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}
}
