package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class RadiusUtils {
	/**
	 * Returns a cube from the center position
	 * **/
	public static AABB cubefromCenter(BlockPos pos, int size) {
		double x1 = pos.getX() - size;
		double y1 = pos.getY() - size;
		double z1 = pos.getZ() - size;
		double x2 = pos.getX() + size;
		double y2 = pos.getY() + size;
		double z2 = pos.getZ() + size;

		//minX minY minZ maxX maxY maxZ
		return new AABB(x1, y1, z1, x2, y2, z2);
	}

	/**
	 * Returns a 9 wide, 9 tall, 9 deep cube from the center position
	 * **/
	public static AABB nineByNineByNineCubeFromCenter(BlockPos centerPos) {
		return cubefromCenter(centerPos, 4);
	}

	/**
	 * Returns a 2 wide, 2 tall, 2 deep cube from the center position
	 * **/
	public static AABB twoByTwoByTwoCubeFromCenter(BlockPos centerPos) {
		return cubefromCenter(centerPos, 2);
	}

	/**
	 * Returns a 9 wide, 1 tall, 9 deep cube from the top position
	 * **/
	public static AABB nineByOneByNineCubeFromTop(BlockPos topPos) {
		double x1 = topPos.getX() - 4;
		double y1 = topPos.getY() + 1;
		double z1 = topPos.getZ() - 4;
		double x2 = topPos.getX() + 4;
		double y2 = topPos.getY() + 1;
		double z2 = topPos.getZ() + 4;

		//minX minY minZ maxX maxY maxZ
		return new AABB(x1, y1, z1, x2, y2, z2);
	}

	/**
	 * Returns a 1 wide, 1 tall, 1 deep cube from the front position
	 * **/
	public static BlockPos oneByOneByOneFromFront(BlockPos frontPos, Direction direction) {
		BlockPos targetPos = switch (direction) {
			case NORTH -> new BlockPos(frontPos.getX(), frontPos.getY(), frontPos.getZ() - 1);
			case SOUTH -> new BlockPos(frontPos.getX(), frontPos.getY(), frontPos.getZ() + 1);
			case EAST -> new BlockPos(frontPos.getX() + 1, frontPos.getY(), frontPos.getZ());
			case WEST  -> new BlockPos(frontPos.getX() - 1, frontPos.getY(), frontPos.getZ());
			default -> throw new IllegalStateException("Unexpected direction");
		};

		return targetPos;
	}

	/**
	 * Returns a 9 wide, 2 tall, 9 deep cube from the front position
	 * **/
	public static AABB nineByTwoByNineCubeFromFront(BlockPos frontPos, Direction direction) {
		double x = frontPos.getX() + 0.5;
		double y = frontPos.getY() + 0.5;
		double z = frontPos.getZ() + 0.5;

		AABB radius = switch(direction) {
			case WEST -> new AABB(x - 1, y, z + 4, x - 9, y + 2, z - 4);
			case EAST -> new AABB(x + 1, y, z + 4, x + 9, y + 2, z - 4);
			case NORTH -> new AABB(x + 4, y, z - 1, x - 4, y + 2, z - 9);
			case SOUTH -> new AABB(x + 4, y, z + 1, x - 4, y + 2, z + 9);
			default -> throw new IllegalStateException("Unexpected direction");
		};

		//minX minY minZ maxX maxY maxZ
		return radius;
	}
}
