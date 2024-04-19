package andronomos.androtech.item;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.AbstractDeviceItem;
import andronomos.androtech.util.BlockPosUtils;
import andronomos.androtech.util.BoundingBoxHelper;
import andronomos.androtech.util.SoundHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class FluidEvaporator extends AbstractDeviceItem {
	public static final String TOOLTIP = "item.androtech.fluid_evaporator.tooltip";
	public static final String TOOLTIP_MODE = "item.androtech.fluid_evaporator.tooltip.mode";
	public static final String TOOLTIP_MODE_WATER = "item.androtech.fluid_evaporator.tooltip.mode.water";
	public static final String TOOLTIP_MODE_LAVA = "item.androtech.fluid_evaporator.tooltip.mode.lava";
	private static final int RANGE = AndroTechConfig.FLUID_EVAPORATOR_RANGE.get();
	private static final String NBT_MODE = "mode";
	public static final int COOLDOWN = 15;

	public enum EvaporateMode implements StringRepresentable {
		WATER, LAVA;

		@Override
		public @NotNull String getSerializedName() {
			return this.name().toLowerCase(Locale.ENGLISH);
		}

		public EvaporateMode getNext() {
			return switch (this) {
				case WATER -> LAVA;
				case LAVA -> WATER;
			};
		}
	}

	public FluidEvaporator(Properties properties) {
		super(properties);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.FLUID_EVAPORATOR_DURABILITY.get();
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		if(player.isCrouching()) {
			toggleMode(player, player.getItemInHand(hand));
		} else {
			removeFluid(level, player, hand);
		}
		return super.use(level, player, hand);
	}

	private void removeFluid(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		BlockPos pos = player.blockPosition();
		ItemStack itemstack = player.getItemInHand(hand);
		EvaporateMode fluidMode = EvaporateMode.values()[itemstack.getOrCreateTag().getInt(NBT_MODE)];
		List<BlockPos> nearbyFluid = switch (fluidMode) {
			case LAVA -> BlockPosUtils.getFluid(BoundingBoxHelper.boxFromCenter(pos, RANGE), level, Fluids.LAVA);
			default -> BlockPosUtils.getFluid(BoundingBoxHelper.boxFromCenter(pos, RANGE), level, Fluids.WATER);
		};
		int countSuccess = 0;
		for (BlockPos posTarget : nearbyFluid) {
			if (removeFlowingLiquid(level, posTarget)) {
				countSuccess++;
			}
		}
		if (countSuccess > 0) {
			player.swing(hand);
			doDamage(itemstack, player,1);
			if (level.isClientSide) {
				SoundHelper.playSound(level, pos, SoundEvents.FIRE_EXTINGUISH);
			}
		}
	}

	private static boolean removeFlowingLiquid(Level world, BlockPos pos) {
		BlockState blockHere = world.getBlockState(pos);
		if (blockHere.hasProperty(BlockStateProperties.WATERLOGGED)) {
			return world.setBlock(pos, blockHere.setValue(BlockStateProperties.WATERLOGGED, false), 18);
		}
		if (blockHere.getBlock() instanceof BucketPickup block) {
			if(block == Blocks.LAVA || block == Blocks.WATER) {
				return world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
		return false;
	}

	private static MutableComponent getModeTooltip(ItemStack stack) {
		EvaporateMode mode = EvaporateMode.values()[stack.getOrCreateTag().getInt(NBT_MODE)];
		return Component.translatable(TOOLTIP_MODE, Component.translatable(mode.getSerializedName() == "water" ? TOOLTIP_MODE_WATER : TOOLTIP_MODE_LAVA));
	}

	public static void toggleMode(Player player, ItemStack stack) {
		if (player.getCooldowns().isOnCooldown(stack.getItem())) {
			return;
		}
		EvaporateMode mode = EvaporateMode.values()[stack.getOrCreateTag().getInt(NBT_MODE)];
		stack.getOrCreateTag().putInt(NBT_MODE, mode.getNext().ordinal());
		player.getCooldowns().addCooldown(stack.getItem(), COOLDOWN);
		if (player.level().isClientSide) {
			player.displayClientMessage(getModeTooltip(stack), true);
		}
	}
}
