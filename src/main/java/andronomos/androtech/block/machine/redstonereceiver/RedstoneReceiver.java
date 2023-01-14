package andronomos.androtech.block.machine.redstonereceiver;

import andronomos.androtech.block.IPoweredMachine;
import andronomos.androtech.block.machine.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RedstoneReceiver extends Machine implements IPoweredMachine {
    public static final String DISPLAY_NAME = "screen.androtech.redstone_receiver";
    public static final String TOOLTIP = "block.androtech.redstone_receiver.tooltip";

    public RedstoneReceiver(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture, boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
        super(properties, useDefaultSideTexture, useDefaultBottomTexture, useDefaultTopTexture, useDefaultFrontTexture, hasMultipleStates);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
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
