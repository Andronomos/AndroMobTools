package andronomos.androtech.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTUtils {
	public static CompoundTag getStackTag(ItemStack stack) {
		if(stack.getTag() == null) {
			return new CompoundTag();
		}
		return stack.getTag();
	}

	public static void setIntVal(ItemStack stack, String prop, int value) {
		stack.getOrCreateTag().putInt(prop, value);
	}

	public static void setStringVal(ItemStack stack, String prop, String value) {
		stack.getOrCreateTag().putString(prop, value);
	}
}
