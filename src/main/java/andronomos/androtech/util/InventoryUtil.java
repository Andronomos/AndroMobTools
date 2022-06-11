package andronomos.androtech.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class InventoryUtil {
	public static boolean inventoryIsFull(IItemHandler handler) {
		AtomicBoolean isFull = new AtomicBoolean(true);

		for(int i = 0; i <= handler.getSlots() - 1; i++) {
			ItemStack itemstack = handler.getStackInSlot(i);

			if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize())
			{
				isFull.set(false);
				break;
			}
		}

		return isFull.get();
	}

	public static ItemStack insertIntoInventory(ItemStack stack, LazyOptional<IItemHandler> itemHandler) {
		AtomicReference<ItemStack> returnStack = new AtomicReference<>(stack.copy());
		itemHandler.ifPresent(h -> {
			for(int i = 0; i < h.getSlots() && !returnStack.get().isEmpty(); ++i) {
				returnStack.set(h.insertItem(i, returnStack.get(), false));
			}
		});
		return returnStack.get();
	}
}
