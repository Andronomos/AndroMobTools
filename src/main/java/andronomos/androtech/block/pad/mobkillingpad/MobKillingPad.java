package andronomos.androtech.block.pad.mobkillingpad;

import andronomos.androtech.block.pad.PadBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class MobKillingPad extends PadBlock implements EntityBlock {
    public static final String DISPLAY_NAME = "screen.androtech.mob_killing_pad";
    public static final String TOOLTIP = "block.androtech.mob_killing_pad.tooltip";

    public MobKillingPad(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MobKillingPadBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);

            if(entity instanceof MobKillingPadBlockEntity mobKillingPadBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, mobKillingPadBlockEntity, entity.getBlockPos());
            } else {
                throw new IllegalStateException("Missing container provider");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level2, pos, state2, blockEntity) -> {
            if(!level.isClientSide()) {
                if(blockEntity instanceof MobKillingPadBlockEntity mobKillingPadBlockEntity) mobKillingPadBlockEntity.serverTick((ServerLevel) level2, pos, state2, mobKillingPadBlockEntity);
            }
        };
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            final MobKillingPadBlockEntity entity = (MobKillingPadBlockEntity)level.getBlockEntity(pos);

            entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
                for(int i = 0; i < itemHandler.getSlots(); i++) {
                    popResource(level, pos, itemHandler.getStackInSlot(i));
                }

                level.updateNeighbourForOutputSignal(pos, this);
            });

            super.onRemove(state,level, pos, newState, isMoving);
        }
    }
}
