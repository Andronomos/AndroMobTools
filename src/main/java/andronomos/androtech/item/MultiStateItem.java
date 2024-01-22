package andronomos.androtech.item;

import andronomos.androtech.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MultiStateItem extends Item {
	public final static String TAG_ACTIVATED = "activated";

	public MultiStateItem(Properties properties) {
		super(properties);
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
}
