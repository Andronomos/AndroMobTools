package andronomos.androtech.block.fluidtank;

import andronomos.androtech.block.base.BaseBlock;
import andronomos.androtech.block.entityvacuum.EntityVacuumBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlock extends BaseBlock implements EntityBlock {
	public static final int SLOTS = 1;
	public static final String DISPLAY_NAME = "screen.androtech.fluid_tank";
	public static final String TOOLTIP = "block.androtech.fluid_tank.tooltip";
	public static final String AMOUNT_TOOLTIP = "screen.androtech.fluid_tank.tooltip.liquid.amount";

	public FluidTankBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, fluidTankBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new FluidTankBlockEntity(pos, state);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
		if(level.isClientSide) {
			return InteractionResult.SUCCESS;
		}

		if(!player.getItemInHand(hand).isEmpty()) {
			ItemStack stack = player.getItemInHand(hand);

			if(stack.getItem() instanceof BucketItem) {
				BlockEntity blockEntity = level.getBlockEntity(pos);

				if(blockEntity instanceof FluidTankBlockEntity) {
					LazyOptional<IFluidHandler> handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER);

					handler.ifPresent((h) -> {
						FluidUtil.interactWithFluidHandler(player, hand, level, pos, result.getDirection());
					});

					return InteractionResult.SUCCESS;
				}
			}
		}

		return super.use(state, level, pos, player, hand, result);
	}
}
