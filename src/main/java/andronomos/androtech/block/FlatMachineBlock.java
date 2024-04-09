package andronomos.androtech.block;

import andronomos.androtech.block.collisioneffect.ICollisionEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class FlatMachineBlock extends MachineBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private final ICollisionEffect collisionEffect;

	public FlatMachineBlock(Properties properties, boolean hasTooltip, ICollisionEffect collisionEffect) {
		super(properties, hasTooltip);
		this.collisionEffect = collisionEffect;
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
}
