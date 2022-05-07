package andronomos.androtech.item.activatableItem;

import andronomos.androtech.Const;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractActivatableItem extends Item implements IActivatableItem {
	public final static String TAG_ACTIVATED = "activated";
	public int tickDelay = Const.TicksInSeconds.FIVESECONDS;
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

	@Override
	public InteractionResultHolder use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(!isActivated(stack) && !isBroken(stack)) {
				activate(stack, player);
			} else {
				deactivate(stack, player);
			}
		}

		return InteractionResultHolder.success(stack);
	}

	public boolean isBroken(ItemStack stack) {
		if(takeDamage) {
			return ItemStackUtil.isBroken(stack);
		}

		return false;
	}

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	private void setActivatedState(ItemStack stack, int activated) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		NBTUtil.setItemStackNBTVal(stack, TAG_ACTIVATED, activated);
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackUtil.damageItem((Player)entity, stack, amount, preventBreaking);
		}
	}

	public void activate(ItemStack stack, Player player) {
		this.setActivatedState(stack, 1);
	}

	public void deactivate(ItemStack stack, Player player) {
		this.setActivatedState(stack, 0);
	}
}
