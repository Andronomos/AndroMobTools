package andronomos.androtech.event.easyharvest;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IReplantHandler {
    InteractionResult handlePlant(ServerLevel world, BlockPos pos, BlockState state, Player player, BlockEntity entity);
}
