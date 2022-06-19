package andronomos.androtech.block.cropfarmer.harvesters;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public interface IHarvester {
	boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, ItemStackHandler itemHandler);
}
