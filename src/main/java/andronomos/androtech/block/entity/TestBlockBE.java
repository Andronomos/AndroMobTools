package andronomos.androtech.block.entity;

import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TestBlockBE extends BlockEntity {
	public TestBlockBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.TEST_BLOCK.get(), pos, state);
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, TestBlockBE testBlock) {

	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, TestBlockBE testBlock) {
		//List<BlockEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), getBlockPos().getX() + 1D, getBlockPos().getY() + 1D, getBlockPos().getZ() + 1D).inflate(0.0625D, 0.0625D, 0.0625D));

	}

}
