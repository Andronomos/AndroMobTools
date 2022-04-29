package andronomos.androtech.item;

import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ChatUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobTransportModule extends Item {
    public static final String TOOLTIP_MOB_DNA_MODULE_MOB = "tooltip.androtech.mob_dna_unit.mob";
    public static final String TOOLTIP_MOB_DNA_MODULE_HEALTH = "tooltip.androtech.mob_dna_unit.health";

    public MobTransportModule(Properties properties) {
        super(properties);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ItemStackUtil.containsEntity(stack) ? 1 : 64;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = player.getItemInHand(context.getHand());

        if (!releaseEntity(stack, player, context.getClickedFace(), context.getLevel(), context.getClickedPos()))
            return InteractionResult.FAIL;

        stack.setTag(null);
        stack.shrink(1);
        if (stack.isEmpty()) player.setItemInHand(context.getHand(), ItemStack.EMPTY);

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if(!captureEntity(stack, player, target)) return InteractionResult.FAIL;
        player.swing(hand);
        stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    public boolean captureEntity(ItemStack stack, Player player, LivingEntity target) {
        Level level = target.level;
        if (level.isClientSide()) return false;
        if (target instanceof Player || !target.isAlive()) return false;
        if (target instanceof WitherBoss) return false;
        if (ItemStackUtil.containsEntity(stack)) return false;
        if (target instanceof Wolf && ((Wolf) target).getRemainingPersistentAngerTime() > 0) return false;
        CompoundTag tag = new CompoundTag();
        target.save(tag);
        ItemStack drop = create(level, target.blockPosition(), tag, EntityType.getKey(target.getType()).toString());
        target.remove(Entity.RemovalReason.KILLED);
        if(!player.addItem(drop)) ItemStackUtil.drop(level, target.blockPosition(), drop);
        return true;
    }

    public boolean releaseEntity(ItemStack stack, Player player, Direction facing, Level world, BlockPos pos) {
        if (player.getCommandSenderWorld().isClientSide()) return false;
        if (!ItemStackUtil.containsEntity(stack)) return false;
        if (facing != null) pos = pos.offset(facing.getNormal());
        Entity entity = ItemStackUtil.getEntityFromStack(stack, world, true);
        entity.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
        if(!world.addFreshEntity(entity)) return false;
        return true;
    }

    public static ItemStack create(Level level, BlockPos pos, CompoundTag tag, String entityName) {
        tag.putString("entity", entityName);
        ItemStack drop = new ItemStack(ModItems.MOB_TRANSPORT_MODULE.get());
        drop.setTag(tag);
        return drop;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(ItemStackUtil.containsEntity(stack)) {
            tooltip.add(new TextComponent(ChatUtil.createTranslation(TOOLTIP_MOB_DNA_MODULE_MOB) + stack.getTag().getString("entity")).withStyle(ChatFormatting.BLUE));
            tooltip.add(new TextComponent(ChatUtil.createTranslation(TOOLTIP_MOB_DNA_MODULE_HEALTH) + stack.getTag().getDouble("Health")).withStyle(ChatFormatting.RED));
        } else {
            super.appendHoverText(stack, levelIn, tooltip, flagIn);
        }
    }
}
