package andronomos.andromobtools.block.pad.emiiter;

import andronomos.andromobtools.block.pad.base.RotatablePadBlock;
import andronomos.andromobtools.block.pad.emiiter.effect.PadEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EmitterPadBlock extends RotatablePadBlock {
	private final PadEffect padEffect;
	private final Boolean shouldAffectPlayer;

	public EmitterPadBlock(Properties properties, PadEffect padEffect, Boolean shouldAffectPlayer) {
		super(properties.strength(5.0F).requiresCorrectToolForDrops());
		this.padEffect = padEffect;
		this.shouldAffectPlayer = shouldAffectPlayer;
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (this.padEffect != null) {
			if(!shouldAffectPlayer) {
				if(entity instanceof Player) {
					return;
				}
			}

			if (entity.isShiftKeyDown()) {
				return;
			}

			this.padEffect.apply(state, world, pos, entity);
		}
	}

	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}

}
