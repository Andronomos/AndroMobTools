package andronomos.androtech.item;

import andronomos.androtech.Constants;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class TickingItem extends MultiStateItem {
	public int tickCounter = 0;
	public boolean takeDamage;

	public TickingItem(Properties properties, boolean takeDamage) {
		super(properties);
		this.takeDamage = takeDamage;
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
		if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) {
			return;
		}

		if(tickCounter == getTickDelay()) {
			tickCounter = 0;
			if(takeDamage) {
				doDamage(stack, entity, 1,false);
			}
			onTick(stack, level, entity, itemSlot, isSelected);
		}

		tickCounter++;
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackHelper.applyDamage((Player)entity, stack, amount, preventBreaking);
		}
	}

	public boolean isBroken(ItemStack stack) {
		if(!takeDamage) {
			return false;
		}
		return ItemStackHelper.isBroken(stack);
	}

	public int getTickDelay() {
		return Constants.TicksInSeconds.THREE;
	}

	public abstract void onTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected);
}
