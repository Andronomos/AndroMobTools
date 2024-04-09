package andronomos.androtech.block.wirelessredstone.redstonetransmitter;

import andronomos.androtech.block.MachineBlock;
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

public class RedstoneSignalTransmitterBlock extends MachineBlock {
	public static final int SLOTS = 1;
	public static final String DISPLAY_NAME = "screen.androtech.redstone_transmitter";
	public static final String TOOLTIP = "block.androtech.redstone_transmitter.tooltip";

	public RedstoneSignalTransmitterBlock(Properties properties) {
		super(properties, false);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new RedstoneSignalTransmitterBlockEntity(pos, state);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof RedstoneSignalTransmitterBlockEntity redstoneSignalTransmitterBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, redstoneSignalTransmitterBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof RedstoneSignalTransmitterBlockEntity transmitterBlockEntity) transmitterBlockEntity.serverTick((ServerLevel) level2, pos, state2, transmitterBlockEntity);
			}
		};
	}
}
