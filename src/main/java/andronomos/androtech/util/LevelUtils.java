package andronomos.androtech.util;

import andronomos.androtech.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

public class LevelUtils {
	//public static void displayWorkArea(AABB area, Level level, Block filler) {
	//	for (double y = area.minY; y <= area.maxY; ++y) {
	//		for (double x = area.minX; x <= area.maxX; ++x) {
	//			for (double z = area.minZ; z <= area.maxZ; ++z) {
	//				BlockPos posInArea = new BlockPos(x, y, z);
	//				BlockState state = level.getBlockState(posInArea);
	//				Block block = state.getBlock();
	//
	//				if(filler != Blocks.AIR) {
	//					if(state == Blocks.AIR.defaultBlockState() || block == Blocks.AIR) {
	//						level.setBlock(posInArea, filler.defaultBlockState(), 0);
	//					}
	//				} else {
	//					if(state == ModBlocks.OVERLAY.get().defaultBlockState() && block == ModBlocks.OVERLAY.get()) {
	//						level.setBlock(posInArea, filler.defaultBlockState(), 0);
	//					}
	//				}
	//			}
	//		}
	//	}
	//}
}
