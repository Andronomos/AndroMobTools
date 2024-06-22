package andronomos.androtech.block;

import andronomos.androtech.block.base.MachineBlock;
import andronomos.androtech.block.collisioneffect.ICollisionEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FlatMachineBlock extends MachineBlock implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private final ICollisionEffect collisionEffect;

	public FlatMachineBlock(Properties properties, ICollisionEffect collisionEffect) {
		super(properties);
		this.collisionEffect = collisionEffect;
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return null;
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean isPossibleToRespawnInThis(@NotNull BlockState p_279289_) {
		return true;
	}

	@Override
	public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float p_180658_4_) {
		//entity.causeFallDamage(p_180658_4_, 0.0F, level.damageSources().fall());
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
		if(collisionEffect == null || entity.isShiftKeyDown() || entity.getY() >= (double) pos.getY() + 0.4d) {
			return;
		}
		collisionEffect.onCollision(state, world, pos, entity);
	}

	@javax.annotation.Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		for(final Direction facing : context.getNearestLookingDirections()) {
			if(facing.getAxis().isHorizontal() && state != null) {
				state = state.setValue(FACING, facing);
				break;
			}
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			final BlockEntity entity = level.getBlockEntity(pos);
			if(entity != null) {
				entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
					for(int i = 0; i < itemHandler.getSlots(); i++) {
						popResource(level, pos, itemHandler.getStackInSlot(i));
					}
					level.updateNeighbourForOutputSignal(pos, this);
				});
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
