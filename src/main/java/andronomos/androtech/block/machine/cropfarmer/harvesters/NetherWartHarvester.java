package andronomos.androtech.block.machine.cropfarmer.harvesters;

import andronomos.androtech.util.InventoryUtils;
import andronomos.androtech.util.ItemStackUtils;
import com.google.common.collect.Iterables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class NetherWartHarvester implements IHarvester {
	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, ItemStackHandler itemHandler) {
		if(!(crop instanceof NetherWartBlock)) {
			return false;
		}

		if(!isReadyForHarvest((NetherWartBlock) crop, cropState)) {
			return false;
		}

		final List<ItemStack> drops = crop.getDrops(cropState, level, pos, null);
		level.destroyBlock(pos, false);
		level.setBlock(pos, Blocks.NETHER_WART.defaultBlockState(), 0);

		for (ItemStack drop : drops) {
			drop.shrink(1);

			ItemStack stack = InventoryUtils.insertIntoInventory(drop, itemHandler, false);

			if (!stack.isEmpty()) {
				ItemStackUtils.drop(level, pos, stack);
			}
		}

		return true;
	}

	private boolean isReadyForHarvest(NetherWartBlock crop, BlockState cropState) {
		int maxAge = Iterables.getLast(crop.AGE.getPossibleValues());
		int age = cropState.getValue(crop.AGE);
		return age == maxAge;
	}
}
