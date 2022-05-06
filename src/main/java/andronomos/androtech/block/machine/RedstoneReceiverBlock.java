package andronomos.androtech.block.machine;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneReceiverBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneReceiverBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.POWERED);
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
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }
}
