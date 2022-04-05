package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.item.MobStorageCellItem;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TestBlockBE extends BlockEntity {
	public TestBlockBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.TEST_BLOCK.get(), pos, state);
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, TestBlockBE testBlock) {

	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, TestBlockBE testBlock) {

	}

}
