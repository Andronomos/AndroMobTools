package andronomos.androtech.item.base;

import andronomos.androtech.Constants;
import andronomos.androtech.config.AndroTechConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class TickingItem extends ToggleableDeviceItem {
	public int tickCounter = 0;

	public TickingItem(Properties properties, boolean hasDurability) {
		super(properties, hasDurability);
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
		if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) {
			return;
		}

		if(tickCounter == getTickDelay()) {
			tickCounter = 0;
			onTick(stack, level, entity, itemSlot, isSelected);
		}

		tickCounter++;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.TICKING_DEVICE_DURABILITY.get();
	}

	public int getTickDelay() {
		return Constants.TicksInSeconds.THREE;
	}

	public abstract void onTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected);
}
