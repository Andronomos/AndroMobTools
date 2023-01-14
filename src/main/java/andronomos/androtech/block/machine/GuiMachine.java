package andronomos.androtech.block.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class GuiMachine extends EntityMachine {
	public GuiMachine(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture,
					  boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
		super(properties,useDefaultSideTexture, useDefaultBottomTexture, useDefaultTopTexture, useDefaultFrontTexture, hasMultipleStates);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			OpenScreen(level, pos, player);
		}

		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	public abstract void OpenScreen(Level level, BlockPos pos, Player player);
}
