package andronomos.androtech.block.machine.pad;

import andronomos.androtech.block.machine.pad.padeffect.PadEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PadEffectBlock extends RotatablePadBlock {
	private final PadEffect padEffect;
	private final Boolean shouldAffectPlayer;

	public PadEffectBlock(Properties properties, PadEffect padEffect, Boolean shouldAffectPlayer) {
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
