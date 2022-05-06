package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredLamp extends Block {
	public ColoredLamp(Properties properties) {
		super(properties.noOcclusion());
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return 15;
	}
}
