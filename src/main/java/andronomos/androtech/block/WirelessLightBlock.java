package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WirelessLightBlock extends DirectionalBlock {
	protected static final VoxelShape SHAPE_UP = Block.box(3, 0, 3, 13, 1, 13);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(3,3,0,13,13,1);
	protected static final VoxelShape SHAPE_WEST = Block.box(15,3,3,15,13,13);
	protected static final VoxelShape SHAPE_DOWN = Block.box(3,15,3,13,15,13);
	protected static final VoxelShape SHAPE_NORTH = Block.box(3,3,15,13, 13,15);
	protected static final VoxelShape SHAPE_EAST = Block.box(0,3,3,0,13,13);

	public WirelessLightBlock(Properties properties) {
		super(properties.lightLevel((s) -> { return 15; }));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
			default :
			case UP :
				return SHAPE_UP;
			case DOWN :
				return SHAPE_DOWN;
			case SOUTH :
				return SHAPE_SOUTH;
			case NORTH :
				return SHAPE_NORTH;
			case WEST :
				return SHAPE_WEST;
			case EAST :
				return SHAPE_EAST;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getClickedFace();
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
		return blockstate.is(this) && blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			super.createBlockStateDefinition(builder);
			builder.add(FACING);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}
}
