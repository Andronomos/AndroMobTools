package andronomos.androtech.block.machine.redstonetransmitter;

import andronomos.androtech.block.IPoweredMachine;
import andronomos.androtech.block.machine.GuiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RedstoneTransmitter extends GuiMachine implements IPoweredMachine {
    public static final String DISPLAY_NAME = "screen.androtech.redstone_transmitter";
    public static final String TOOLTIP = "block.androtech.redstone_transmitter.tooltip";

    public RedstoneTransmitter(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture, boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
        super(properties, useDefaultSideTexture, useDefaultBottomTexture, useDefaultTopTexture, useDefaultFrontTexture, hasMultipleStates);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.POWERED);
    }

    @Override
    public void OpenScreen(Level level, BlockPos pos, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);

        if(entity instanceof RedstoneTransmitterBlockEntity redstoneTransmitterBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) player, redstoneTransmitterBlockEntity, entity.getBlockPos());
        } else {
            throw new IllegalStateException("Missing container provider");
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneTransmitterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level2, pos, state2, blockEntity) -> {
            if(!level.isClientSide()) {
                if(blockEntity instanceof RedstoneTransmitterBlockEntity redstoneTransmitter) redstoneTransmitter.serverTick((ServerLevel) level2, pos, state2, redstoneTransmitter);
            }
        };
    }
}
