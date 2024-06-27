package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityVacuumBlock extends MachineBlock {
	public static final int SLOTS = 21;
	public static final String DISPLAY_NAME = "screen.androtech.entity_vacuum";
	public static final String TOOLTIP = "block.androtech.entity_vacuum.tooltip";
	public static final String AMOUNT_TOOLTIP = "screen.androtech.entity_vacuum.tooltip.liquid.amount";

	public EntityVacuumBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new EntityVacuumBlockEntity(pos, state);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof EntityVacuumBlockEntity entityVacuumBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, entityVacuumBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof EntityVacuumBlockEntity itemAttractor) itemAttractor.serverTick((ServerLevel) level2, pos, state2, itemAttractor);
			}
		};
	}
}
