package andronomos.androtech.block;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.entity.TestBlockBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends Block implements EntityBlock  {
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
