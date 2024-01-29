package andronomos.androtech.item;

import andronomos.androtech.util.ItemStackHelper;
import andronomos.androtech.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MultiStateItem extends Item {
	public final static String TAG_ACTIVATED = "activated";
	public boolean takeDamage;

	public MultiStateItem(Properties properties, boolean takeDamage) {
		super(properties);
		this.takeDamage = takeDamage;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
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
		CompoundTag nbtTag = NBTHelper.getStackTag(stack);
		if(!nbtTag.contains(TAG_ACTIVATED)) return false;
		return nbtTag.getInt(TAG_ACTIVATED) == 1;
	}

	public void activate(ItemStack stack) {
		this.setActivatedState(stack, 1);
	}

	public void deactivate(ItemStack stack) {
		this.setActivatedState(stack, 0);
	}

	private void setActivatedState(ItemStack stack, int activated) {
		NBTHelper.setIntVal(stack, TAG_ACTIVATED, activated);
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
}
