package andronomos.androtech.block.machine.redstonereceiver;

import andronomos.androtech.block.IPoweredBlock;
import andronomos.androtech.block.machine.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RedstoneReceiver extends Machine implements IPoweredBlock {
    public static final String DISPLAY_NAME = "screen.androtech.redstone_receiver";
    public static final String TOOLTIP = "block.androtech.redstone_receiver.tooltip";

    public RedstoneReceiver(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
        this.hasMultipleStates = true;
        setTexture("top_off", "redstone_receiver_off_top");
        setTexture("side_off", "redstone_receiver_off_side");
        setTexture("bottom_off", "redstone_receiver_off_bottom");
        setTexture("top_on", "redstone_receiver_on_top");
        setTexture("side_on", "redstone_receiver_on_side");
        setTexture("bottom_on", "redstone_receiver_on_bottom");
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
    public boolean isSignalSource(BlockState state) {
        return true;
    }
}
