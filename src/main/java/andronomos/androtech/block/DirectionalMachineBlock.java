package andronomos.androtech.block;

import andronomos.androtech.base.MachineBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

public class DirectionalMachineBlock extends MachineBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	public DirectionalMachineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}

	@javax.annotation.Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction).setValue(POWERED, false);
	}

	//@Override
	//public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
	//	return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	//}

	//@Override
	//public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
	//	return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	//}
}
