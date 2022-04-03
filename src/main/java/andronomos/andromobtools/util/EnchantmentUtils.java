package andronomos.andromobtools.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentUtils {
	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
		return EnchantmentHelper.getEnchantments(stack).containsKey(enchantment);
	}
}
