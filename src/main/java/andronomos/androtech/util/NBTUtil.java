package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTUtil {

    public static void setBlockPos(ItemStack stack, BlockPos pos) {
        if (pos == null || stack.isEmpty()) {
            return;
        }
        NBTUtil.setIntVal(stack, "xpos", pos.getX());
        NBTUtil.setIntVal(stack, "ypos", pos.getY());
        NBTUtil.setIntVal(stack, "zpos", pos.getZ());
    }

    public static void setIntVal(ItemStack item, String prop, int value) {
        item.getOrCreateTag().putInt(prop, value);
    }

    /* */
    public static CompoundTag getItemStackTag(ItemStack itemStackIn) {
        if(itemStackIn.getTag() == null) {
            return new CompoundTag();
        }
        return itemStackIn.getTag();
    }
}
