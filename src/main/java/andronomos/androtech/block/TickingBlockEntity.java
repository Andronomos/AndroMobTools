package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickingBlockEntity {
	void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);
	void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity);
	boolean shouldTick();
}
