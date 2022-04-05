package andronomos.androtech.item;

import andronomos.androtech.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbstractActivatableItem extends Item {
	public final static String TAG_STATUS = "activated";

	public AbstractActivatableItem(Properties properties) {
		super(properties);
	}

	public boolean canUse(ItemStack stack) {
		//if the item damage is not being ignored and the item has durability
		if(stack.getMaxDamage() > 0)
			//make sure the item has durability left
			return stack.getDamageValue() < stack.getMaxDamage();
		return true;
	}

	public boolean isActivated(ItemStack stack) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		if(!nbtTag.contains(TAG_STATUS)) return false;
		return nbtTag.getInt(TAG_STATUS) == 1;
	}

	public void setState(ItemStack stack, int activated) {
		CompoundTag nbtTag = NBTUtil.getItemStackTag(stack);
		NBTUtil.setItemStackNBTVal(stack, TAG_STATUS, activated);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack stack) {
		return isActivated(stack);
	}
}
