package andronomos.androtech.item;

import andronomos.androtech.Const;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractActivatableItem extends Item implements IActivatableItem {
	public final static String TAG_ACTIVATED = "activated";
	public final int tickDelay = Const.TicksInSeconds.FIVESECONDS;
	public boolean takeDamage;
	public boolean isRepairable;
	public int tickCounter = 0;

	public AbstractActivatableItem(Properties properties) {
		super(properties);
		this.takeDamage = false;
		this.isRepairable = false;
	}

	public AbstractActivatableItem(Properties properties, boolean takeDamage, boolean isRepairable) {
		super(properties);
		this.takeDamage = takeDamage;
		this.isRepairable = isRepairable;

		if(!this.isRepairable) {
			properties.setNoRepair();
		}
	}

	public boolean isBroken(ItemStack stack) {
		if(takeDamage) {
			int maxDamage = stack.getMaxDamage();

			if(maxDamage > 0) {
				int currentDamage = stack.getDamageValue();

				if (currentDamage == 0) return false;

				//An item's damage value actually increments when taking damage
				return currentDamage >= maxDamage;
			}

			if(stack.getMaxDamage() > 0) {
				if (stack.getDamageValue() > 0) {
					return false;
				} else {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	public void setActivated(ItemStack stack, int activated) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		NBTUtil.setItemStackNBTVal(stack, TAG_ACTIVATED, activated);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack stack) {
		return isActivated(stack);
	}

	public abstract void doDamage(ItemStack stack, Entity entity);
	public abstract void deactivate(ItemStack stack, Player player);
	public abstract void activate(ItemStack stack, Player player);
}
