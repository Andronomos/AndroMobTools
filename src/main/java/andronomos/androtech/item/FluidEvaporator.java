package andronomos.androtech.item;

import andronomos.androtech.util.BoundingBoxHelper;
import andronomos.androtech.util.ItemStackHelper;
import andronomos.androtech.util.SoundHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FluidEvaporator extends Item {
	private static final int SIZE = 4;
	private static final String NBT_MODE = "mode";

	public enum EvaporateMode implements StringRepresentable {
		WATER, LAVA;

		@Override
		public String getSerializedName() {
			return this.name().toLowerCase(Locale.ENGLISH);
		}

		public EvaporateMode getNext() {
			switch (this) {
				case WATER:
					return LAVA;
				case LAVA:
					return WATER;
			}
			return WATER;
		}
	}

	public FluidEvaporator(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
		Direction face = context.getClickedFace();
		ItemStack itemstack = context.getItemInHand();
		EvaporateMode fluidMode = EvaporateMode.values()[itemstack.getOrCreateTag().getInt(NBT_MODE)];
		List<BlockPos> area = cubeSquareBase(pos.relative(face), SIZE, 1);
		switch (fluidMode) {
			case LAVA:
				break;
			case WATER:
				break;
			default:
				break;
		}
		int countSuccess = 0;
		boolean tryHere = false;
		for (BlockPos posTarget : area) {
			BlockState blockHere = world.getBlockState(posTarget);
			FluidState fluidHere = blockHere.getFluidState();
			if (fluidHere == null) {
				continue;
			}
			tryHere = false;
			if (fluidMode == EvaporateMode.WATER && fluidHere.is(FluidTags.WATER)) {
				tryHere = true;
			}
			else if (fluidMode == EvaporateMode.LAVA && fluidHere.is(FluidTags.LAVA)) {
				tryHere = true;
			}
			if (tryHere && removeFlowingLiquid(world, posTarget, true)) {
				countSuccess++;
			}
		}
		if (countSuccess > 0) {
			Player player = context.getPlayer();
			player.swing(context.getHand());
			ItemStackHelper.applyDamage(player, itemstack, 1);
			if (world.isClientSide) {
				SoundHelper.playSound(world, pos, SoundEvents.FIRE_EXTINGUISH);
			}
		}
		return InteractionResult.SUCCESS;
	}

	private static List<BlockPos> cubeSquareBase(final BlockPos pos, int radius, int height) {
		List<BlockPos> shape = new ArrayList<BlockPos>();
		// search in a cube
		int xMin = pos.getX() - radius;
		int xMax = pos.getX() + radius;
		int zMin = pos.getZ() - radius;
		int zMax = pos.getZ() + radius;
		for (int x = xMin; x <= xMax; x++) {
			for (int z = zMin; z <= zMax; z++) {
				for (int y = pos.getY(); y <= pos.getY() + height; y++) {
					//now go max height on each pillar for sort order
					shape.add(new BlockPos(x, y, z));
				}
			}
		}
		return shape;
	}

	private static boolean removeFlowingLiquid(Level world, BlockPos pos, boolean nukeOption) {
		BlockState blockHere = world.getBlockState(pos);
		if (blockHere.hasProperty(BlockStateProperties.WATERLOGGED)) {
			// un-water log
			return world.setBlock(pos, blockHere.setValue(BlockStateProperties.WATERLOGGED, false), 18);
		}
		if (blockHere.getBlock() instanceof BucketPickup) {
			BucketPickup block = (BucketPickup) blockHere.getBlock();
			//
			ItemStack res = block.pickupBlock(world, pos, blockHere);
			if (!res.isEmpty()) {
				// flowing block
				return world.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
			}
			else {
				return true; // was source block
			}
		}
		else if (nukeOption) {
			//ok just nuke it
			return world.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
		}
		return false;
	}
}
