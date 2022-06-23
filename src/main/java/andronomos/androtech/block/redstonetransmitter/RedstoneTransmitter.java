package andronomos.androtech.block.redstonetransmitter;

import andronomos.androtech.block.ATGuiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RedstoneTransmitter extends ATGuiMachine {
    public static final String name = "screen.androtech.redstone_transmitter";

    public RedstoneTransmitter(BlockBehaviour.Properties properties) {
        super(properties, false, false, true, false, true, false);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneTransmitterBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level2, pos, state2, blockEntity) -> {
            if(!level.isClientSide()) {
                if(blockEntity instanceof RedstoneTransmitterBE redstoneTransmitter) redstoneTransmitter.serverTick((ServerLevel) level2, pos, state2, redstoneTransmitter);
            }
        };
    }

    @Override
    public void OpenGui(Level level, BlockPos pos, Player player) {
        BlockEntity be = level.getBlockEntity(pos);

        if(be instanceof RedstoneTransmitterBE) {
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return new TranslatableComponent(name);
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                    return new RedstoneTransmitterContainer(windowId, pos, playerInventory);
                }
            };
            NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
        }
    }
}
