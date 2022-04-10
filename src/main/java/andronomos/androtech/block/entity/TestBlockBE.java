package andronomos.androtech.block.entity;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.block.TestBlock;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class TestBlockBE extends BlockEntity implements TickingBlockEntity {
	private int tickDelay = Const.TicksInSeconds.FIVESECONDS;
	private int tickCounter = 0;

	public TestBlockBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.TEST_BLOCK.get(), pos, state);
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity testBlock) {

	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity testBlock) {
		if(!shouldTick()) {
			return;
		}

		List<BlockPos> blocksInArea = getNearbyBlocks(pos);

		for (BlockPos nearbyPos : blocksInArea) {
			BlockState nearbyState = level.getBlockState(nearbyPos);

			if (nearbyState.getBlock() instanceof TestBlock) {
				continue;
			}

			Block nearbyBlock = nearbyState.getBlock();

			if (nearbyBlock instanceof IPlantable) {
				PlantType type = ((IPlantable)nearbyBlock).getPlantType(level, nearbyPos);

				if(type == PlantType.CROP) {
					CropBlock crop = (CropBlock)nearbyBlock;

					if(crop.isMaxAge(nearbyState)) {
						//harvestCrop(crop, nearbyState, level, nearbyPos).forEach(stack -> ItemHandlerHelper.giveItemToPlayer(level.player, stack));
					}
				}
			}

			//level.setBlockAndUpdate(nearbyPos, Blocks.LIME_STAINED_GLASS.defaultBlockState());
		}
	}




	private List<ItemStack> harvestCrop(CropBlock crop, BlockState cropState, ServerLevel level, BlockPos pos) {
		final List<ItemStack> drops = crop.getDrops(cropState, level, pos, null);
		level.destroyBlock(pos, false);
		level.setBlock(pos, crop.getStateForAge(0), 7);
		for (ItemStack drop : drops) {
			drop.shrink(1);
		}
		return drops;
	}







	private List<BlockPos> getNearbyBlocks(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		AABB area = new AABB(x - 1, y + 1, z - 1, x + 1, y + 1, z + 1);
		List<BlockPos> blocksInArea = BlockUtils.getBlockPosInAABB(area);
		return blocksInArea;
	}

	public boolean shouldTick() {
		if (tickCounter < tickDelay) {
			tickCounter++;
			return false;
		} else {
			tickCounter = 0;
		}
		return true;
	}
}
