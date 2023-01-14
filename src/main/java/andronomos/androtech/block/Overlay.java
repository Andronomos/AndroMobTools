package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Overlay extends GlassBlock {
	public Overlay() {
		super(Block.Properties.of(Material.GLASS)
				.strength(0.3F).sound(SoundType.GLASS).noOcclusion());
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	//@Override
	//public VoxelShape getVisualShape(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_, CollisionContext p_48738_) {
	//	return Shapes.empty();
	//}

	//@Override
	//public RenderShape getRenderShape(BlockState p_60550_) {
	//	return RenderShape.INVISIBLE;
	//}

	//@Override
	//public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
	//	return false;
	//}

	//@Override
	//public boolean isOcclusionShapeFullBlock(BlockState p_222959_, BlockGetter p_222960_, BlockPos p_222961_) {
	//	return false;
	//}

	@Override
	public float getExplosionResistance() {
		return 100.0f;
	}
}
