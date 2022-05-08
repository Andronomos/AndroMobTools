package andronomos.androtech.block.machine;

import andronomos.androtech.block.entity.RedstoneTransmitterBE;
import andronomos.androtech.block.machine.base.AbstractToggleableMachine;
import andronomos.androtech.inventory.RedstoneTransmitterContainer;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RedstoneTransmitterBlock extends AbstractToggleableMachine {
    public static final String SCREEN_REDSTONE_TRANSMITTER = "screen.androtech.redstone_transmitter";

    public RedstoneTransmitterBlock(Properties properties) {
        super(properties);
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()) {
            if(player.isCrouching()) {
                OpenGui(level, pos, player);
            } else {
                toggleState(state, level, pos, player);

                if(level.getBlockEntity(pos) instanceof RedstoneTransmitterBE) {
                    level.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                        BlockState transmitterState = level.getBlockState(pos);
                        for(int slotIndex = 0; slotIndex < 9; slotIndex++) {
                            ItemStack blockGPSModule = itemHandler.getStackInSlot(slotIndex);
                            if(!blockGPSModule.isEmpty()) {
                                BlockPos receiverPos = NBTUtil.getItemStackBlockPos(blockGPSModule);
                                BlockState receiverState = level.getBlockState(receiverPos);
                                if(receiverState.getBlock() != ModBlocks.REDSTONE_RECEIVER.get()) return;
                                if(transmitterState.getValue(POWERED))
                                    level.setBlock(receiverPos, receiverState.setValue(POWERED, true), 3);
                                else
                                    level.setBlock(receiverPos, receiverState.setValue(POWERED, false), 3);
                            }
                        }
                    });
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void OpenGui(Level level, BlockPos pos, Player player) {
        BlockEntity be = level.getBlockEntity(pos);

        if(be instanceof RedstoneTransmitterBE) {
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return new TranslatableComponent(SCREEN_REDSTONE_TRANSMITTER);
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
