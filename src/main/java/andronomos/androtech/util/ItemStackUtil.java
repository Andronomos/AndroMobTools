package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class ItemStackUtil {
    public static void applyDamage(LivingEntity player, ItemStack stack, int amount) {
        applyDamage(player, stack, amount, false);
    }

    public static void applyDamage(LivingEntity player, ItemStack stack, int amount, boolean preventBreaking) {
        if(preventBreaking) {
            if(stack.getDamageValue() == stack.getMaxDamage()) {
                return;
            }

            stack.hurt(amount, player.getRandom(), player instanceof ServerPlayer ? (ServerPlayer)player : null);
        } else {
            stack.hurtAndBreak(amount, player, (p) -> {
                p.broadcastBreakEvent(InteractionHand.MAIN_HAND);
            });
        }
    }

    public static void drop(Level level, BlockPos pos, ItemStack drop) {
        if (!level.isClientSide()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop));
        }
    }

    public static boolean isRepairable(ItemStack stack) {
        if(stack.isEmpty()) return false; //if the item doesn't exist
        if(!stack.isRepairable()) return false; //if the item can't be repaired
        if(stack.getMaxDamage() == 0) return false; //if the item can't be damaged
        if(stack.getDamageValue() == 0) return false; //if the item hasn't taken any damage
        //if(stack.getDamageValue() == stack.getMaxDamage()) return false; //if the item can't take anymore damage
        return true;
    }

    public static boolean isBroken(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();

        if(maxDamage > 0) {
            //An item's damage value actually increments when taking damage
            return stack.getDamageValue() >= maxDamage;
        }

        return false;
    }

    public static boolean hasEntityTag(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
    }

    @Nullable
    public static Entity getEntity(ItemStack stack, Level world, boolean withInfo) {
        EntityType type = EntityType.byString(stack.getTag().getString("entity")).orElse(null);
        if (type != null) {
            Entity entity = type.create(world);
            if (withInfo)
                entity.load(stack.getTag());
            return entity;
        }
        return null;
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
}
