package andronomos.androtech.item.device.base;

import andronomos.androtech.Const;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AbstractTickingDevice extends AbstractToggleableDevice implements ITickingDevice {
	public static final int DURABILITY = 1000;
	public int tickDelay = Const.TicksInSeconds.FIVESECONDS;
	public int tickCounter = 0;

	public AbstractTickingDevice(Properties properties) {
		super(properties, false, false);
	}

	public AbstractTickingDevice(Properties properties, boolean takeDamage, boolean isRepairable) {
		super(properties, takeDamage, isRepairable);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return DURABILITY;
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
