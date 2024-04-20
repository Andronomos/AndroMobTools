package andronomos.androtech.block.mobrepulsor;

import andronomos.androtech.block.DirectionalMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings("ALL")
public class MobRepulsorBlock extends DirectionalMachineBlock {
	public static final String DISPLAY_NAME = "screen.androtech.mob_repulsor";
	public static final String TOOLTIP = "block.androtech.mob_repulsor.tooltip";
	public static final int SLOTS = 3;

	public MobRepulsorBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new MobRepulsorBlockEntity(pos, state);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof MobRepulsorBlockEntity entityRepulsorBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, entityRepulsorBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof MobRepulsorBlockEntity entityRepulsorBlockEntity) entityRepulsorBlockEntity.serverTick((ServerLevel) level2, pos, state2, entityRepulsorBlockEntity);
			}
		};
	}

	@Nonnull
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
}
