package andronomos.androtech.block.collisioneffect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CollisionEffectJumpBoost implements ICollisionEffect {
	private final double velocity;

	public CollisionEffectJumpBoost(double velocity) {
		this.velocity = velocity;
	}

	@Override
	public void onCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.setDeltaMovement(entity.getDeltaMovement().add(0, this.velocity * (Direction.UP.getStepY() * 1.5), 0));
	}
}
