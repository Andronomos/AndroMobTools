package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
	public static List<BlockPos> getBlockPosInAABB(AABB axisAlignedBB) {
		List<BlockPos> blocks = new ArrayList<>();

		for (double y = axisAlignedBB.minY; y <= axisAlignedBB.maxY; ++y) {
			for (double x = axisAlignedBB.minX; x <= axisAlignedBB.maxX; ++x) {
				for (double z = axisAlignedBB.minZ; z <= axisAlignedBB.maxZ; ++z) {
					blocks.add(new BlockPos(x, y, z));
				}
			}
		}

		return blocks;
	}

	public static List<BlockPos> getCropsInArea(AABB scanArea, Level level) {
		List<BlockPos> crops = new ArrayList<>();
		List<BlockPos> blocksInArea = getBlockPosInAABB(scanArea);

		for (BlockPos nearbyPos : blocksInArea) {
			BlockState nearbyState = level.getBlockState(nearbyPos);

			if(nearbyState == Blocks.AIR.defaultBlockState()) continue;

			Block nearbyBlock = nearbyState.getBlock();

			if (nearbyBlock instanceof IPlantable) {
				PlantType type = ((IPlantable)nearbyBlock).getPlantType(level, nearbyPos);

				if(type == PlantType.CROP || type == PlantType.NETHER) {
					crops.add(nearbyPos);
				}

				if(nearbyBlock instanceof SweetBerryBushBlock) {
					crops.add(nearbyPos);
				}
			}

			if(nearbyBlock instanceof StemGrownBlock) {
				crops.add(nearbyPos);
			}
		}
		return crops;
	}

	public static List<BlockPos> getOreInArea(AABB scanArea, Level level) {
		List<BlockPos> ore = new ArrayList<>();
		List<BlockPos> blocksInArea = getBlockPosInAABB(scanArea);

		for (BlockPos blockPos : blocksInArea) {
			Block block = level.getBlockState(blockPos).getBlock();

			if(isOre(block)) {
				ore.add(blockPos);
			}
		}

		return ore;
	}

	public static boolean isOre(Block block) {
		if(block instanceof OreBlock ||
				block instanceof RedStoneOreBlock ||
				block == Blocks.RAW_COPPER_BLOCK ||
				block == Blocks.RAW_GOLD_BLOCK ||
				block == Blocks.RAW_IRON_BLOCK)
			return true;

		return false;
	}
}
