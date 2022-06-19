package andronomos.androtech.inventory;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

/**
 * ItemStackHandler wrapper that that only allows a specific item to be inserted.
 */
public class MachineSlotItemStackHandler extends ItemStackHandler {
	private final Item allowedItem;

	public MachineSlotItemStackHandler(Item allowedItem) {
		super();
		this.allowedItem = allowedItem;
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		return stack.getItem() == allowedItem;
	}
}
