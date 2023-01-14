package andronomos.androtech.block.machine.cropfarmer.harvesters;

import andronomos.androtech.util.InventoryUtils;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class SugarcaneHarvester implements IHarvester {
	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, ItemStackHandler itemHandler) {
		if(!(crop instanceof SugarCaneBlock)) {
			return false;
		}

		if(!isReadyForHarvest(level, pos)) {
			return false;
		}

		level.destroyBlock(pos, false);
		level.destroyBlock(pos.above(2), false);
		level.destroyBlock(pos.above(1), false);
		level.setBlock(pos, Blocks.SUGAR_CANE.defaultBlockState(), 0);

		ItemStack stack = new ItemStack(Items.SUGAR_CANE);
		stack.setCount(2);
		stack = InventoryUtils.insertIntoInventory(stack, itemHandler, false);

		if (!stack.isEmpty()) {
			ItemStackUtils.drop(level, pos, stack);
		}

		return false;
	}

	private boolean isReadyForHarvest(ServerLevel level, BlockPos pos) {
		Block block = level.getBlockState(pos.above(2)).getBlock();

		if(block instanceof SugarCaneBlock) {
			return true;
		}

		return false;
	}
}
