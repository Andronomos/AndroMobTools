package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

	public static boolean isBroken(ItemStack stack) {
		int maxDamage = stack.getMaxDamage();
		if(maxDamage > 0) {
			//An item's damage value actually increments when taking damage
			return stack.getDamageValue() >= maxDamage;
		}
		return false;
	}

	public static void drop(Level level, BlockPos pos, ItemStack drop) {
		if (!level.isClientSide()) {
			level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop));
		}
	}
}
