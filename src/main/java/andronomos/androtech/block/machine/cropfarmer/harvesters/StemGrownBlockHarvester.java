package andronomos.androtech.block.machine.cropfarmer.harvesters;

import andronomos.androtech.util.InventoryUtils;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class StemGrownBlockHarvester implements IHarvester {
	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, ItemStackHandler itemHandler) {
		if(!(crop instanceof StemGrownBlock)) {
			return false;
		}

		final List<ItemStack> drops = crop.getDrops(cropState, level, pos, null);
		level.destroyBlock(pos, false);
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);

		for (ItemStack drop : drops) {
			ItemStack stack = InventoryUtils.insertIntoInventory(drop, itemHandler, false);

			if (!stack.isEmpty()) {
				ItemStackUtils.drop(level, pos, stack);
			}
		}

		return true;
	}
}
