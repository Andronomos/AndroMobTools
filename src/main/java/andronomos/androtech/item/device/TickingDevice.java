package andronomos.androtech.item.device;

import andronomos.androtech.Const;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TickingDevice extends ToggleableDevice {
	public static int durability = 1000;
	public int tickDelay = Const.TicksInSeconds.THREE;
	public int tickCounter = 0;

	public TickingDevice(Item.Properties properties) {
		super(properties, false);
	}

	public TickingDevice(Item.Properties properties, boolean takeDamage) {
		super(properties, takeDamage);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return durability;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

		if(isBroken(stack)) {
			deactivate(stack, (Player)entity);
			return;
		}

		if(tickCounter == tickDelay) {
			if(takeDamage) {
				doDamage(stack, entity, 1,false);
			}

			tickCounter = 0;
		}

		tickCounter++;
	}
}
