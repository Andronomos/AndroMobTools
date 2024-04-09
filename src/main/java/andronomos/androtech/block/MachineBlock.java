package andronomos.androtech.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

public class MachineBlock extends BaseMachine implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final String GUI_ON = "gui.androtech.powered_on";
	public static final String GUI_OFF = "gui.androtech.powered_off";

	public MachineBlock(Properties properties)
	{
		this(properties, false);
	}

	public MachineBlock(Properties properties, boolean hasTooltip) {
		super(properties, hasTooltip);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
	}

	@javax.annotation.Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction).setValue(POWERED, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(POWERED);
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
