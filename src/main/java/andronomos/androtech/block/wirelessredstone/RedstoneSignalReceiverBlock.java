package andronomos.androtech.block.wirelessredstone;

import andronomos.androtech.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class RedstoneSignalReceiverBlock extends MachineBlock {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);
	public static final String DISPLAY_NAME = "screen.androtech.redstone_receiver";
	public static final String TOOLTIP = "block.androtech.redstone_receiver.tooltip";

	public RedstoneSignalReceiverBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getSignal(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull Direction side) {
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getDirectSignal(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull Direction side) {
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public boolean isSignalSource(@NotNull BlockState state) {
		return true;
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}
}
