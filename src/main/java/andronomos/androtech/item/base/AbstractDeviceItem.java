package andronomos.androtech.item.base;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractDeviceItem extends Item {
	public static int DURABILITY = AndroTechConfig.DEVICE_DURABILITY.get();
	public boolean hasDurability;

	public AbstractDeviceItem(Properties properties) {
		this(properties, AndroTechConfig.DEVICE_TAKE_DAMAGE.get());
	}

	public AbstractDeviceItem(Properties properties, boolean hasDurability) {
		super(properties);
		this.hasDurability = hasDurability;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.DEVICE_DURABILITY.get();
	}

	public void doDamage(ItemStack stack, Entity entity, int amount) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackHelper.applyDamage((Player)entity, stack, amount);
		}
	}

	public boolean isBroken(ItemStack stack) {
		if(hasDurability) {
			return ItemStackHelper.isBroken(stack);
		}
		return false;
	}
}
