package andronomos.androtech.block.wirelessredstone;

import andronomos.androtech.block.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class RedstoneSignalReceiverBlock extends MachineBlock {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);
	public static final String DISPLAY_NAME = "screen.androtech.redstone_receiver";
	public static final String TOOLTIP = "block.androtech.redstone_receiver.tooltip";

	public RedstoneSignalReceiverBlock(Properties properties) {
		super(properties, true, false, false);
		addTexture("top_off", "redstone_signal_receiver_off_top");
		addTexture("side_off", "redstone_signal_receiver_off_side");
		addTexture("bottom_off", "redstone_signal_receiver_off_bottom");
		addTexture("top_on", "redstone_signal_receiver_on_top");
		addTexture("side_on", "redstone_signal_receiver_on_side");
		addTexture("bottom_on", "redstone_signal_receiver_on_bottom");
	}

	@Override
	public int getSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction side) {
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getDirectSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction side) {
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}
}
