package andronomos.andromobutils.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTUtil {

    public static void setItemStackBlockPos(ItemStack stack, BlockPos pos) {
        if (pos == null || stack.isEmpty()) {
            return;
        }
        NBTUtil.setItemStackNBTVal(stack, "xpos", pos.getX());
        NBTUtil.setItemStackNBTVal(stack, "ypos", pos.getY());
        NBTUtil.setItemStackNBTVal(stack, "zpos", pos.getZ());
    }

    public static void setItemStackNBTVal(ItemStack item, String prop, int value) {
        if (item.isEmpty()) return;
        item.getOrCreateTag().putInt(prop, value);
    }

    public static BlockPos getItemStackBlockPos(ItemStack stack) {
        if (stack.isEmpty() || !stack.getOrCreateTag().contains("xpos")) {
            return null;
        }
        CompoundTag tag = stack.getOrCreateTag();
        return getBlockPos(tag);
    }

    public static BlockPos getBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("xpos"), tag.getInt("ypos"), tag.getInt("zpos"));
    }

    /* */
    public static CompoundTag getItemStackTag(ItemStack itemStackIn) {
        if(itemStackIn.getTag() == null) {
            return new CompoundTag();
        }
        return itemStackIn.getTag();
    }

}
