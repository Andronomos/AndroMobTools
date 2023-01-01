package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BlockPosUtils {
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
		if(block instanceof DropExperienceBlock ||
				block instanceof RedStoneOreBlock ||
				block == Blocks.RAW_COPPER_BLOCK ||
				block == Blocks.RAW_GOLD_BLOCK ||
				block == Blocks.RAW_IRON_BLOCK)
			return true;

		return false;
	}

	public static List<BlockPos> getFluid(AABB scanArea, Level level, FlowingFluid fluid) {
		List<BlockPos> fluidFound = new ArrayList<>();
		List<BlockPos> nearbyFluid = getBlockPosInAABB(scanArea);

		for (BlockPos pos : nearbyFluid) {
			FluidState fluidState = level.getBlockState(pos).getFluidState();

			if(fluidState != null && fluidState.getType() == fluid) {
				fluidFound.add(pos);
			}
		}

		return fluidFound;
	}
}
