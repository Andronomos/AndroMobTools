package andronomos.androtech.inventory.server;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class RestrictedSlotHandler extends SlotItemHandler {
	ItemStack item;
	int maxItems;

	public RestrictedSlotHandler(IItemHandler itemHandler, int slotIndex, int x, int y, ItemStack item, int maxItems) {
		super(itemHandler, slotIndex, x, y);
		this.item = item;
		this.maxItems = maxItems;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		//return stack.getItem() == item.getItem() && stack.getDamageValue() == item.getDamageValue();
		return stack.getItem() == item.getItem();
	}

	@Override
	public int getMaxStackSize() {
		return maxItems;
	}

	@Override
	public int getMaxStackSize(@Nonnull ItemStack stack) {
		return this.getMaxStackSize();
	}
}
