package andronomos.androtech.block.pad.emiiter.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface PadEffect {
	void apply (BlockState state, Level world, BlockPos pos, Entity entity);
}
