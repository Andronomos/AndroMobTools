package andronomos.androtech.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class DamagePadUpgradeItem extends Item {
	public Enchantment enchantment;

	public DamagePadUpgradeItem(Properties properties, Enchantment enchantment) {
		super(properties);
		this.enchantment = enchantment;
	}
}
