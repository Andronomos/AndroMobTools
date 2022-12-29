package andronomos.androtech.item.base;

import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AbstractDevice extends Item {
	public static int DURABILITY = 2031;
	public boolean takeDamage = true;

	public AbstractDevice(Properties properties) {
		super(properties);
	}

	public AbstractDevice(Properties properties, boolean takeDamage) {
		super(properties);
		this.takeDamage = takeDamage;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return DURABILITY;
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackUtils.applyDamage((Player)entity, stack, amount, preventBreaking);
		}
	}
}
