package andronomos.androtech.block.machine;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

/**
 * ItemStackHandler wrapper that that only allows a specific item to be inserted.
 */
public class MachineSlotItemStackHandler extends ItemStackHandler {
	protected final Item allowedItem;
	protected final BlockEntity be;

	public MachineSlotItemStackHandler(BlockEntity be, Item allowedItem) {
		this(be, allowedItem, 1);
	}

	public MachineSlotItemStackHandler(BlockEntity be, Item allowedItem, int size) {
		super(size);
		this.be = be;
		this.allowedItem = allowedItem;
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		return stack.getItem() == allowedItem;
	}

	@Override
	protected void onContentsChanged(int slot) {
		// To make sure the TE persists when the chunk is saved later we need to
		// mark it dirty every time the item handler changes
		be.setChanged();
	}
}
