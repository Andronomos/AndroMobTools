package andronomos.androtech.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class FakeSword extends SwordItem {
	public FakeSword(Properties properties) {
		super(Tiers.DIAMOND, 3, -2.4F, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.hurt(attacker.damageSources().playerAttack((Player)attacker), getDamage() + EnchantmentHelper.getDamageBonus(stack, target.getMobType()));
		return true;
	}
}
