package andronomos.androtech.item.base;

import andronomos.androtech.Const;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TickingDevice extends ToggleableDevice {
	public int tickDelay = Const.TicksInSeconds.THREE;
	public int tickCounter = 0;

	public TickingDevice(Properties properties) {
		super(properties);
	}

	public TickingDevice(Properties properties, boolean takeDamage) {
		super(properties, takeDamage);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

		if(tickCounter == tickDelay) {
			if(takeDamage) {
				doDamage(stack, entity, 1,false);
			}

			tickCounter = 0;
		}

		tickCounter++;
	}
}
