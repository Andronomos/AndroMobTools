package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AndroTechTickingMachine extends AndroTechMachine implements EntityBlock {
	public AndroTechTickingMachine(Properties properties) {
		this(properties, true, true, false, false);
	}

	public AndroTechTickingMachine(Properties properties, boolean useDefaultTopTexture, boolean useDefaultBottomTexture, boolean hasMultipleStates, boolean isDirectional) {
		super(properties, useDefaultTopTexture, useDefaultBottomTexture, hasMultipleStates, isDirectional);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			OpenGui(level, pos, player);
		}

		return InteractionResult.SUCCESS;
	}

	public abstract void OpenGui(Level level, BlockPos pos, Player player);
}
