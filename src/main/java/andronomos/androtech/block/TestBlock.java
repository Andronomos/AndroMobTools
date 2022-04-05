package andronomos.androtech.block;

import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.block.entity.TestBlockBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends Block implements EntityBlock {
	public TestBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return null;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(level.isClientSide()) {
				if(blockEntity instanceof TestBlockBE testBlock) testBlock.clientTick(level2, pos, state2, testBlock);
			} else {
				if(blockEntity instanceof TestBlockBE testBlock) testBlock.serverTick((ServerLevel) level2, pos, state2, testBlock);
			}
		};
	}
}
