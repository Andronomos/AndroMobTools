package andronomos.androtech.item;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.AbstractDeviceItem;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobStorageDevice extends AbstractDeviceItem {
	public static final String TOOLTIP_MOB_STORAGE_DEVICE_MOB = "tooltip.androtech.mob_storage_device.mob";
	public static final String TOOLTIP_MOB_STORAGE_DEVICE_HEALTH = "tooltip.androtech.mob_storage_device.health";

	public MobStorageDevice(Properties properties) {
		super(properties, AndroTechConfig.MOB_STORAGE_DEVICE_TAKE_DAMAGE.get());
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.MOB_STORAGE_DEVICE_DURABILITY.get();
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		if(context.getLevel().isClientSide()) return InteractionResult.PASS;
		Player player = context.getPlayer();
		ItemStack stack = player.getItemInHand(context.getHand());
		if (!ItemStackHelper.hasEntityTag(stack)) return InteractionResult.PASS;
		if (!releaseEntity(stack, player, context.getClickedFace(), context.getLevel(), context.getClickedPos())) return InteractionResult.FAIL;
		stack.setTag(null);
		return InteractionResult.SUCCESS;
	}

	@Override
	public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
		if(player.level().isClientSide()) return InteractionResult.PASS;
		if(ItemStackHelper.hasEntityTag(stack)) return InteractionResult.FAIL;
		if(!captureEntity(stack, target)) return InteractionResult.FAIL;
		player.swing(hand);
		return InteractionResult.SUCCESS;
	}

	public boolean captureEntity(ItemStack stack, LivingEntity entity) {
		if(!entityIsValid(entity)) return false;
		ItemStackHelper.saveEntity(entity, stack);
		if(ItemStackHelper.hasEntityTag(stack)) {
			entity.remove(Entity.RemovalReason.KILLED);
		}
		return true;
	}

	public boolean releaseEntity(ItemStack stack, Player player, Direction facing, Level level, BlockPos pos) {
		if (facing != null) pos = pos.offset(facing.getNormal());
		Entity entity = ItemStackHelper.createEntity(stack, level, true);
		if (entity == null) return false;
		entity.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
		if(!level.addFreshEntity(entity)) return false;
		if(hasDurability) doDamage(stack, player, 1, AndroTechConfig.MOB_STORAGE_DEVICE_CAN_BREAK.get());
		return true;
	}

	public boolean entityIsValid(LivingEntity entity) {
		if (entity instanceof Player || !entity.isAlive() || entity instanceof WitherBoss) {
			return false;
		}
		return !(entity instanceof Wolf) || ((Wolf) entity).getRemainingPersistentAngerTime() <= 0;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(ItemStackHelper.hasEntityTag(stack)) {
			tooltip.add(Component.translatable(TOOLTIP_MOB_STORAGE_DEVICE_MOB, stack.getTag().getString("Entity")).withStyle(ChatFormatting.BLUE));
			tooltip.add(Component.translatable(TOOLTIP_MOB_STORAGE_DEVICE_HEALTH, stack.getTag().getDouble("Health")).withStyle(ChatFormatting.RED));
		} else {
			super.appendHoverText(stack, level, tooltip, flag);
		}
	}
}
