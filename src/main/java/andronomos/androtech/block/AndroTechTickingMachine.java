package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AndroTechTickingMachine extends AndroTechMachine implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public AndroTechTickingMachine(Properties properties) {
		this(properties, true, true, false);
	}

	public AndroTechTickingMachine(Properties properties, boolean useDefaultTopTexture, boolean useDefaultBottomTexture, boolean hasmultipleStates) {
		super(properties, useDefaultTopTexture, useDefaultBottomTexture, hasmultipleStates);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
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
