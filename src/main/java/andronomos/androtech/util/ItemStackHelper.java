package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemStackHelper {
	public static BlockPos getBlockPos(ItemStack stack) {
		if(stack.isEmpty()) return null;
		CompoundTag tag = stack.getOrCreateTag();
		int xPos = tag.getInt("xpos");
		int yPos = tag.getInt("ypos");
		int zPos = tag.getInt("zpos");
		if(xPos == 0 && yPos == 0 && zPos == 0) {
			return null;
		}
		return new BlockPos(xPos, yPos, zPos);
	}
}
