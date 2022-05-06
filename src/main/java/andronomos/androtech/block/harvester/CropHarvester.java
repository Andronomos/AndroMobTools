package andronomos.androtech.block.harvester;

import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class CropHarvester implements IHarvester {
	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, LazyOptional<IItemHandler> itemHandler) {
		if(!(crop instanceof CropBlock)) {
			return false;
		}

		CropBlock cropBlock = (CropBlock) crop;

		if(!isReadyForHarvest(cropBlock, cropState)) {
			return false;
		}

		final List<ItemStack> drops = cropBlock.getDrops(cropState, level, pos, null);
		level.destroyBlock(pos, false);
		level.setBlock(pos, cropBlock.getStateForAge(0), 7);

		ItemStack defaultSeedDrop = cropBlock.getCloneItemStack(level, pos, cropState);
		Item seed = defaultSeedDrop.isEmpty() ? Items.WHEAT_SEEDS : defaultSeedDrop.getItem();

		for (ItemStack drop : drops) {
			if(seed != null && seed == drop.getItem()) {
				drop.shrink(1);
			}

			ItemStack stack = ItemStackUtil.insertIntoContainer(drop, itemHandler);

			if (!stack.isEmpty()) {
				ItemStackUtil.drop(level, pos, stack);
			}
		}

		return true;
	}

	private boolean isReadyForHarvest(CropBlock crop, BlockState cropState) {
		return crop.isMaxAge(cropState);
	}
}
