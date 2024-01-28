package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemStackHelper {
	public static void applyDamage(LivingEntity player, ItemStack stack, int amount, boolean preventBreaking) {
		if(preventBreaking) {
			if(stack.getDamageValue() == stack.getMaxDamage()) {
				return;
			}
			stack.hurt(amount, player.getRandom(), player instanceof ServerPlayer ? (ServerPlayer)player : null);
		} else {
			stack.hurtAndBreak(amount, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		}
	}

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
