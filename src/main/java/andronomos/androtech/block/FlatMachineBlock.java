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

public class FlatMachineBlock extends MachineBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private final ICollisionEffect collisionEffect;

	public FlatMachineBlock(Properties properties, boolean hasTooltip, boolean isDirectional, String topTexture, ICollisionEffect collisionEffect) {
		super(properties, false, hasTooltip, isDirectional);
		this.collisionEffect = collisionEffect;
		addTexture("top", topTexture);
		addTexture("bottom", "machine_bottom");
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean isPossibleToRespawnInThis(BlockState p_279289_) {
		return true;
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float p_180658_4_) {
		//entity.causeFallDamage(p_180658_4_, 0.0F, level.damageSources().fall());
		return;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(level, pos, state, entity);
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (collisionEffect == null) {
			return;
		}

		if (entity.isShiftKeyDown()) {
			return;
		}

		collisionEffect.onCollision(state, world, pos, entity);
	}
}
