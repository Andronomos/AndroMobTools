package andronomos.androtech.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public class NaniteEnhancedDiggerItem extends DiggerItem {

	public NaniteEnhancedDiggerItem(Tier tier, float attackDamage, float attackSpeed, TagKey<Block> p_204111_, Properties properties) {
		super(attackDamage, attackSpeed, tier, p_204111_, properties);
	}
}
