package andronomos.androtech.block.cropfarmer.harvesters;

import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class SweetBerryHarvester implements IHarvester {


	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, LazyOptional<IItemHandler> itemHandler) {
		if(!(crop instanceof SweetBerryBushBlock)) {
			return false;
		}

		SweetBerryBushBlock berryBush = (SweetBerryBushBlock)crop;

		if(!isReadyForHarvest(berryBush, cropState)) {
			return false;
		}

		boolean flag = getAge(berryBush, cropState) == 3;

		int dropAmount = 1 + level.random.nextInt(2);
		//Block.popResource(level, pos, new ItemStack(Items.SWEET_BERRIES, dropAmount + (flag ? 1 : 0)));

		ItemStack drop = new ItemStack(Items.SWEET_BERRIES.asItem(), dropAmount + (flag ? 1 : 0));
		ItemStack stack = ItemStackUtil.insertIntoContainer(drop, itemHandler);

		if (!stack.isEmpty()) {
			ItemStackUtil.drop(level, pos, stack);
		}

		level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
		level.setBlock(pos, cropState.setValue(berryBush.AGE, Integer.valueOf(1)), 2);

		return true;
	}

	private boolean isReadyForHarvest(SweetBerryBushBlock bush, BlockState cropState) {
		int age = getAge(bush, cropState);
		return age > 1;
	}

	private int getAge(SweetBerryBushBlock bush, BlockState cropState) {
		return cropState.getValue(bush.AGE);
	}
}
