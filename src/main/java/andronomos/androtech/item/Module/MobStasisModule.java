package andronomos.androtech.item.Module;

import andronomos.androtech.item.base.AbstractDevice;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobStasisModule extends AbstractDevice {
	public static final String TOOLTIP_MOB_STASIS_MODULE_MOB = "tooltip.androtech.mob_stasis_module.mob";
	public static final String TOOLTIP_MOB_STASIS_MODULE_HEALTH = "tooltip.androtech.mob_stasis_module.health";

	public MobStasisModule(Properties properties) {
		super(properties);
		DURABILITY = 1;
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return ItemStackUtils.hasEntityTag(stack) ? 1 : 64;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		ItemStack stack = player.getItemInHand(context.getHand());

		if (!releaseEntity(stack, player, context.getClickedFace(), context.getLevel(), context.getClickedPos()))
			return InteractionResult.FAIL;

		stack.setTag(null);

		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		if(!captureEntity(stack, player, target, stack.getCount())) return InteractionResult.FAIL;
		player.swing(hand);

		if(stack.getCount() > 1) {
			stack.shrink(1);
		}

		return InteractionResult.SUCCESS;
	}

	public boolean captureEntity(ItemStack stack, Player player, LivingEntity target, int stackCount) {
		Level level = target.level;
		if(!entityIsValid(target)) return false;
		CompoundTag tag = new CompoundTag();
		target.save(tag);

		if(stackCount > 1) {
			ItemStack drop = create(level, target.blockPosition(), tag, EntityType.getKey(target.getType()).toString());
			if(!player.addItem(drop)) ItemStackUtils.drop(level, target.blockPosition(), drop);
		} else {
			stack.setTag(tag);
		}

		target.remove(Entity.RemovalReason.KILLED);

		return true;
	}

	public boolean releaseEntity(ItemStack stack, Player player, Direction facing, Level world, BlockPos pos) {
		if (player.getCommandSenderWorld().isClientSide()) return false;
		if (!ItemStackUtils.hasEntityTag(stack)) return false;
		if (facing != null) pos = pos.offset(facing.getNormal());
		Entity entity = ItemStackUtils.getEntity(stack, world, true);
		entity.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
		if(!world.addFreshEntity(entity)) return false;

		if(takeDamage) {
			doDamage(stack, player, 1, false);
		}

		return true;
	}

	public boolean entityIsValid(LivingEntity entity) {
		Level level = entity.level;
		if (level.isClientSide()) return false;
		if (entity instanceof Player || !entity.isAlive() || entity instanceof WitherBoss) return false;
		if (entity instanceof Wolf && ((Wolf) entity).getRemainingPersistentAngerTime() > 0) return false;

		return true;
	}

	public static ItemStack create(Level level, BlockPos pos, CompoundTag tag, String entityName) {
		tag.putString("entity", entityName);
		ItemStack drop = new ItemStack(ModItems.MOB_STASIS_MODULE.get());
		drop.setTag(tag);
		return drop;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if(ItemStackUtils.hasEntityTag(stack)) {
			tooltip.add(Component.translatable(TOOLTIP_MOB_STASIS_MODULE_MOB + stack.getTag().getString("entity")).withStyle(ChatFormatting.BLUE));
			tooltip.add(Component.translatable(TOOLTIP_MOB_STASIS_MODULE_HEALTH + stack.getTag().getDouble("Health")).withStyle(ChatFormatting.RED));
		} else {
			super.appendHoverText(stack, level, tooltip, flag);
		}
	}
}
