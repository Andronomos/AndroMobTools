package andronomos.androtech.block;

import andronomos.androtech.block.collisioneffect.CollisionEffectAcceleration;
import andronomos.androtech.block.collisioneffect.ICollisionEffect;

public class FlatMachineEffects {
	public static ICollisionEffect ACCELERATION_WEAK = new CollisionEffectAcceleration(.06d);
	public static ICollisionEffect ACCELERATION_NORMAL = new CollisionEffectAcceleration(0.3d);
	public static ICollisionEffect ACCELERATION_STRONG = new CollisionEffectAcceleration(1.5d);

	public static ICollisionEffect JUMP_WEAK = new CollisionEffectAcceleration(1.5d);
}
