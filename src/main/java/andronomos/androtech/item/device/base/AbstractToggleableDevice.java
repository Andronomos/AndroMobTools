package andronomos.androtech.item.device.base;

import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractToggleableDevice extends AbstractDevice implements IToggleableDevice {
	public final static String TAG_ACTIVATED = "activated";

	public AbstractToggleableDevice(Properties properties) {
		super(properties, false, false);
	}

	public AbstractToggleableDevice(Properties properties, boolean takeDamage, boolean isRepairable) {
		super(properties, takeDamage, isRepairable);
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

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = NBTUtil.getStackTag(stack);
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	private void setActivatedState(ItemStack stack, int activated) {
		CompoundTag nbtTag = NBTUtil.getStackTag(stack);
		NBTUtil.setIntVal(stack, TAG_ACTIVATED, activated);
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackUtil.applyDamage((Player)entity, stack, amount, preventBreaking);
		}
	}

	public void activate(ItemStack stack, Player player) {
		this.setActivatedState(stack, 1);
	}

	public void deactivate(ItemStack stack, Player player) {
		this.setActivatedState(stack, 0);
	}
}
