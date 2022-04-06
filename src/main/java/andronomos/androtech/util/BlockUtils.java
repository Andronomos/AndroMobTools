package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
	public static List<BlockPos> getBlockPosInAABB(AABB axisAlignedBB) {
		List<BlockPos> blocks = new ArrayList<>();
		for (double y = axisAlignedBB.minY; y < axisAlignedBB.maxY; ++y) {
			for (double x = axisAlignedBB.minX; x < axisAlignedBB.maxX; ++x) {
				for (double z = axisAlignedBB.minZ; z < axisAlignedBB.maxZ; ++z) {
					blocks.add(new BlockPos(x, y, z));
				}
			}
		}
		return blocks;
	}
}
