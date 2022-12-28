package andronomos.androtech.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTUtils {
	public static CompoundTag getStackTag(ItemStack itemStackIn) {
		if(itemStackIn.getTag() == null) {
			return new CompoundTag();
		}
		return itemStackIn.getTag();
	}

	public static void setIntVal(ItemStack item, String prop, int value) {
		item.getOrCreateTag().putInt(prop, value);
	}

	public static void setStringVal(ItemStack item, String prop, String value) {
		item.getOrCreateTag().putString(prop, value);
	}
}
