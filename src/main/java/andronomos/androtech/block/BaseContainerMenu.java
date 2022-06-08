package andronomos.androtech.block;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class BaseContainerMenu extends AbstractContainerMenu {
	protected Player player;
	protected Inventory inventory;

	protected BaseContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventoryIn) {
		super(type, id);
		this.inventory = inventoryIn;
		this.player = this.inventory.player;
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

	public void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Player inventory
		addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
	}
}
