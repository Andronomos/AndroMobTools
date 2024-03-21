package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TeleportInhibitor extends MachineBlock {
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 7, 14);

	public TeleportInhibitor(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}
}
