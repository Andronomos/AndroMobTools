package andronomos.androtech.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class FakeSword extends SwordItem {
    public FakeSword() {
        super(Tiers.NETHERITE, (int)Tiers.NETHERITE.getAttackDamageBonus(), 1000, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurt(DamageSource.playerAttack((Player) attacker), getDamage() + EnchantmentHelper.getDamageBonus(stack, target.getMobType()));
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }
}
