package andronomos.androtech.item.base;

import andronomos.androtech.util.ItemStackHelper;
import andronomos.androtech.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ToggleableDeviceItem extends AbstractDeviceItem {
	public final static String TAG_ACTIVATED = "activated";

	public ToggleableDeviceItem(Properties properties, boolean hasDurability) {
		super(properties, hasDurability);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		if(!level.isClientSide) {
			ItemStack stack = player.getItemInHand(hand);
			if(!isActivated(stack)) {
				activate(stack);
			} else {
				deactivate(stack);
			}
		}
		return super.use(level, player, hand);
	}

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = stack.getOrCreateTag();
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	public void activate(ItemStack stack) {
		NBTHelper.setIntVal(stack, TAG_ACTIVATED, 1);
	}

	public void deactivate(ItemStack stack) {
		NBTHelper.setIntVal(stack, TAG_ACTIVATED, 0);
	}

	public void doDamage(ItemStack stack, Entity entity, int amount) {
		ItemStackHelper.applyDamage((Player)entity, stack, amount);
	}

	public boolean isBroken(ItemStack stack) {
		return hasDurability && ItemStackHelper.isBroken(stack);
	}
}
