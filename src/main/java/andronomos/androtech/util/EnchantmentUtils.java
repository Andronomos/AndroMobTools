package andronomos.androtech.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentUtils {
	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
		return EnchantmentHelper.getEnchantments(stack).containsKey(enchantment);
	}

	//public static int getEnchantmentLevel(ItemStack book, Enchantment enchantment) {
	//	CompoundTag tag = NBTUtil.getStackTag(book);
	//
	//	AndroTech.LOGGER.info("EnchantmentUtil#getEnchantmentLevel | tag = {}", tag);
	//
	//	//ListTag storedEnchants = tag.get("StoredEnchantments");
	//	ListTag storedEnchants = tag.getList("StoredEnchantments", );
	//
	//
	//
	//	return 0;
	//}
}
