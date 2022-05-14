package andronomos.androtech.block.pad;

import andronomos.androtech.block.entity.MobKillingPadBE;
import andronomos.androtech.block.pad.base.PadBlock;
import andronomos.androtech.inventory.MobKillingPadContainer;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class MobKillingPadBlock extends PadBlock implements EntityBlock {
    public static final String SCREEN_MOB_KILLING_PAD = "screen.androtech.mob_killing_pad";

    public MobKillingPadBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MobKillingPadBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level2, pos, state2, blockEntity) -> {
            if(!level.isClientSide()) {
                if(blockEntity instanceof MobKillingPadBE mobKillingPadBE) mobKillingPadBE.serverTick((ServerLevel) level2, pos, state2, mobKillingPadBE);
            }
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide) {
            BlockEntity tile = level.getBlockEntity(pos);

            if(tile instanceof MobKillingPadBE) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(SCREEN_MOB_KILLING_PAD);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new MobKillingPadContainer(windowId, pos, playerInventory);
                    }
                };

                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tile.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            final MobKillingPadBE entity = (MobKillingPadBE)level.getBlockEntity(pos);

            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
                for(int i = 0; i < itemHandler.getSlots(); i++) {
                    popResource(level, pos, itemHandler.getStackInSlot(i));
                }

                level.updateNeighbourForOutputSignal(pos, this);
            });

            super.onRemove(state,level, pos, newState, isMoving);
        }
    }
}
