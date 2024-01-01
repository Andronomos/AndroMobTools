package andronomos.androtech.block;

import andronomos.androtech.block.collisioneffect.CollisionEffectAcceleration;
import andronomos.androtech.block.collisioneffect.CollisionEffectJumpBoost;
import andronomos.androtech.block.collisioneffect.ICollisionEffect;

public class FlatMachineEffects {
	public static ICollisionEffect ACCELERATION_WEAK = new CollisionEffectAcceleration(.06d);
	public static ICollisionEffect ACCELERATION_NORMAL = new CollisionEffectAcceleration(0.3d);
	public static ICollisionEffect ACCELERATION_STRONG = new CollisionEffectAcceleration(1.5d);

	public static ICollisionEffect JUMP_WEAK = new CollisionEffectJumpBoost(.06d);
	public static ICollisionEffect JUMP_NORMAL = new CollisionEffectJumpBoost(0.3D);
	public static ICollisionEffect JUMP_STRONG = new CollisionEffectJumpBoost(1.5d);
}
