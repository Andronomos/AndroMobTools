package andronomos.androtech.item.base;

import andronomos.androtech.util.ItemStackUtils;
import andronomos.androtech.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToggleableDevice extends AbstractDevice {
	public final static String TAG_ACTIVATED = "activated";

	public ToggleableDevice(Properties properties) {
		super(properties, false);
	}

	public ToggleableDevice(Properties properties, boolean takeDamage) {
		super(properties, takeDamage);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(!isActivated(stack)) {
				activate(stack);
			} else {
				deactivate(stack);
			}
		}

		return super.use(level, player, hand);
	}

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = NBTUtils.getStackTag(stack);
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	public void activate(ItemStack stack) {
		this.setActivatedState(stack, 1);
	}

	public void deactivate(ItemStack stack) {
		this.setActivatedState(stack, 0);
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackUtils.applyDamage((Player)entity, stack, amount, preventBreaking);
		}
	}

	private void setActivatedState(ItemStack stack, int activated) {
		NBTUtils.setIntVal(stack, TAG_ACTIVATED, activated);
	}
}
