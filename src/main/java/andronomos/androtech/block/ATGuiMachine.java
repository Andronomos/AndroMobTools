package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ATGuiMachine extends ATMachine implements EntityBlock {
	public ATGuiMachine(Properties properties) {
		super(properties);
	}

	public ATGuiMachine(Properties properties, boolean hasTopTexture, boolean hasBottomTexture, boolean hasSideTexture, boolean hasFrontTexture, boolean hasMultiStates, boolean isDirectional) {
		super(properties, hasTopTexture, hasBottomTexture, hasSideTexture, hasFrontTexture, hasMultiStates, isDirectional);
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
