package andronomos.androtech.block.machine;

import net.minecraft.world.level.block.EntityBlock;

public abstract class EntityMachine extends Machine implements EntityBlock {
	public EntityMachine(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture,
						 boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
		super(properties, useDefaultSideTexture, useDefaultBottomTexture, useDefaultTopTexture, useDefaultFrontTexture, hasMultipleStates);
	}
}
