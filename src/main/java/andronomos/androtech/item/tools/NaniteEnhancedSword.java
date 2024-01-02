package andronomos.androtech.item.tools;

import andronomos.androtech.Constants;
import andronomos.androtech.config.AndroTechConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class NaniteEnhancedSword extends SwordItem {
	public int tickDelay = Constants.TicksInSeconds.THREE;
	public int tickCounter = 0;

	public NaniteEnhancedSword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.NANITE_SWORD_DURABILITY.get();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !(entity instanceof Player)) return;
		if(this.tickCounter == this.tickDelay) {
			this.tickCounter = 0;
			if(stack.isDamaged()) {
				stack.setDamageValue(stack.getDamageValue() - AndroTechConfig.NANITE_REPAIR_RATE.get());
			}
		}
		this.tickCounter++;
	}
}
