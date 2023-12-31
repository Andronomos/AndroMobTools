package andronomos.androtech.block.base.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ATGUIMachineBlock extends ATMachineBlock {
	public ATGUIMachineBlock(Properties properties, boolean hasMultipleStates) {
		super(properties, hasMultipleStates);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide) {
			OpenScreen(level, pos, player);
		}

		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	public abstract void OpenScreen(Level level, BlockPos pos, Player player);
}
