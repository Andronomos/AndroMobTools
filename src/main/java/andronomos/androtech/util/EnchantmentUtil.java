package andronomos.androtech.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentUtil {
	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
		return EnchantmentHelper.getEnchantments(stack).containsKey(enchantment);
	}

	public int getEnchantmentLevel(ItemStack book, Enchantment enchantment) {
		CompoundTag tag = book.getTag();



		return 0;
	}
}
