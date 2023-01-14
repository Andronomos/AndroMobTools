package andronomos.androtech.block.machine;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class MachineMenu extends AbstractContainerMenu {
	protected Player player;
	protected Inventory inventory;

	protected static int playerInventoryFirstSlot;
	protected static int playerInventoryLastSlot;
	protected static int blockEntityFirstSlot;
	protected static int blockEntityLastSlot;

	protected MachineMenu(@Nullable MenuType<?> type, int id, Inventory inv) {
		super(type, id);
		this.inventory = inv;
		this.player = this.inventory.player;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotId) {
		Slot sourceSlot = slots.get(slotId);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if(slotId >= playerInventoryFirstSlot) {
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
		return copyOfSourceStack;
	}

	public void setupSlotIndexs(int slotCount)  {
		this.blockEntityFirstSlot = 0;
		this.blockEntityLastSlot = slotCount > 1 ? slotCount : 1; //moveItemStackTo requires 2 different slotIds to work properly so the last slot must be larger than the first
		this.playerInventoryFirstSlot = slotCount > 1 ? slotCount + 1 : slotCount;
		this.playerInventoryLastSlot = slotCount > 1 ? slotCount + Const.PLAYER_INVENTORY_SIZE : Const.PLAYER_INVENTORY_SIZE;

		//AndroTech.LOGGER.info("MachineMenu#setupSlotIndexs | slotCount >> {}", slotCount);
		//AndroTech.LOGGER.info("MachineMenu#setupSlotIndexs | blockEntityFirstSlot >> {}", this.blockEntityFirstSlot);
		//AndroTech.LOGGER.info("MachineMenu#setupSlotIndexs | blockEntityLastSlot >> {}", this.blockEntityLastSlot);
		//AndroTech.LOGGER.info("MachineMenu#setupSlotIndexs | playerInventoryFirstSlot >> {}", this.playerInventoryFirstSlot);
		//AndroTech.LOGGER.info("MachineMenu#setupSlotIndexs | playerInventoryLastSlot >> {}", this.playerInventoryLastSlot);
	}

	public void addLargeInventory(IItemHandler handler) {
		for (int y = 0; y < 6; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Const.MENU_SLOT_X_OFFSET + x * Const.SCREEN_SLOT_SIZE,
						Const.SCREEN_SLOT_SIZE + y * Const.SCREEN_SLOT_SIZE));
			}
		}
	}

	public void addMachineInventory(IItemHandler handler) {
		for(int y = 0; y < 3; y++) {
			for (int x = 0; x < 7; x++) {
				AndroTech.LOGGER.info("MachineMenu#addMachineInventory | x pos >> {}", Const.MENU_SLOT_MACHINE_X_OFFSET + x * Const.SCREEN_SLOT_SIZE);

				addSlot(new SlotItemHandler(handler, x + y * 7,
						Const.MENU_SLOT_X_OFFSET + x * Const.SCREEN_SLOT_SIZE,
						Const.MENU_SLOT_SIZE + y * Const.SCREEN_SLOT_SIZE));
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
