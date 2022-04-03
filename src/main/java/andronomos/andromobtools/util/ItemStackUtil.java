package andronomos.andromobtools.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ItemStackUtil {

    /* */
    public static void damageItem(LivingEntity player, ItemStack stack, int amount) {
        stack.hurtAndBreak(amount, player, (p) -> {
            p.broadcastBreakEvent(InteractionHand.MAIN_HAND);
        });
    }

    /* */
    public static void drop(Level level, BlockPos pos, ItemStack drop) {
        if (!level.isClientSide()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop));
        }
    }

    /* */
    public static boolean containsEntity(ItemStack stack)
    {
        return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
    }

    /* Returns the entity in the stack */
    @Nullable
    public static Entity getEntityFromStack(ItemStack stack, Level world, boolean withInfo)
    {
        EntityType type = EntityType.byString(stack.getTag().getString("entity")).orElse(null);
        if (type != null) {
            Entity entity = type.create(world);
            if (withInfo)
                entity.load(stack.getTag());
            return entity;
        }
        return null;
    }





}
