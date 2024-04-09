package andronomos.androtech.block.entityrepolsor;

import andronomos.androtech.block.MachineBlock;
import andronomos.androtech.block.itemattractor.ItemAttractorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityRepulsorBlock extends MachineBlock {
	public static final String DISPLAY_NAME = "screen.androtech.entity_repulsor";
	public static final String TOOLTIP = "block.androtech.entity_repulsor.tooltip";
	public static final int SLOTS = 3;

	public EntityRepulsorBlock(Properties properties) {
		super(properties, false);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new EntityRepulsorBlockEntity(pos, state);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof EntityRepulsorBlockEntity entityRepulsorBlockEntity) {
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
				if(blockEntity instanceof EntityRepulsorBlockEntity entityRepulsorBlockEntity) entityRepulsorBlockEntity.serverTick((ServerLevel) level2, pos, state2, entityRepulsorBlockEntity);
			}
		};
	}
}
