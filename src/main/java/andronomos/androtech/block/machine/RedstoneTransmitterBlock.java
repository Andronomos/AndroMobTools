package andronomos.androtech.block.machine;

import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.block.entity.RedstoneTransmitterBE;
import andronomos.androtech.inventory.RedstoneTransmitterContainer;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.util.ItemStackUtil;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RedstoneTransmitterBlock extends Block implements EntityBlock {
    public static final String SCREEN_REDSTONE_TRANSMITTER = "screen.androtech.redstone_transmitter";
    //protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneTransmitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
    }

    //@Override
    //public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
    //    return SHAPE;
    //}
    //
    //@Override
    //public VoxelShape getCollisionShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext context) {
    //    return SHAPE;
    //}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneTransmitterBE(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level2, pos, state2, blockEntity) -> {
            if(level.isClientSide()) {
                if(blockEntity instanceof RedstoneTransmitterBE redstoneTransmitter) redstoneTransmitter.clientTick(level2, pos, state2, redstoneTransmitter);
            } else {
                if(blockEntity instanceof RedstoneTransmitterBE redstoneTransmitter) redstoneTransmitter.serverTick((ServerLevel) level2, pos, state2, redstoneTransmitter);
            }
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()) {
            if(player.isCrouching()) {
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
            } else {
                level.setBlock(pos, state.cycle(POWERED), 3);
                final RedstoneTransmitterBE blockEntity = (RedstoneTransmitterBE)level.getBlockEntity(pos);
                blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                    BlockState transmitterState = level.getBlockState(pos);
                    for(int slotIndex = 0; slotIndex < 9; slotIndex++) {
                        ItemStack receiverCard = itemHandler.getStackInSlot(slotIndex);
                        if(!receiverCard.isEmpty()) {
                            BlockPos receiverPos = NBTUtil.getItemStackBlockPos(receiverCard);
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

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity entity = level.getBlockEntity(pos);

            if(entity instanceof RedstoneTransmitterBE) {
                entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
                    for(int i = 0; i <= itemHandler.getSlots() - 1; i++) {
                        popResource(level, pos, itemHandler.getStackInSlot(i));
                    }
                });
            }

            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state,level, pos, newState, isMoving);
    }
}
