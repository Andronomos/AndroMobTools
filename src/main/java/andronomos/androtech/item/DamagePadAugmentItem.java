package andronomos.androtech.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class DamagePadAugmentItem extends Item {
	public Enchantment enchantment;

	public DamagePadAugmentItem(Properties properties, Enchantment enchantment) {
		super(properties);
		this.enchantment = enchantment;
	}
}
