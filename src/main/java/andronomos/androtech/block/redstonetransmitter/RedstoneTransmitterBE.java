package andronomos.androtech.block.redstonetransmitter;

import andronomos.androtech.block.TickingMachineBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class RedstoneTransmitterBE extends TickingMachineBlockEntity {
	public static final int TRANSMITTER_SLOTS = 9;

    public RedstoneTransmitterBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_TRANSMITTER.get(), pos, state);
    }

    /**
     *  Keeps a receiver synced with the transmitter when is placed in the world
     */
    private void updateReceiver(BlockPos receiverPos) {
        if(receiverPos == null) return; //this should only happen when the receiver card doesn't have any coords
        BlockState receiverState = level.getBlockState(receiverPos);
        if(receiverState == null) return;
        if(receiverState.getBlock() != ModBlocks.REDSTONE_RECEIVER.get()) return;
        boolean receiverIsPowered = receiverState.getValue(BlockStateProperties.POWERED);
        boolean transmitterIsPowered = level.getBlockState(this.worldPosition).getValue(BlockStateProperties.POWERED);
        if(receiverIsPowered != transmitterIsPowered) {
            level.setBlockAndUpdate(receiverPos, receiverState.setValue(BlockStateProperties.POWERED, transmitterIsPowered));
        }
    }

    @Override
    protected ItemStackHandler createInventoryItemHandler() {
        return new ItemStackHandler(RedstoneTransmitterBE.TRANSMITTER_SLOTS) {

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(stack.getItem() == ModItems.BLOCK_GPS_RECORDER.get()) {
                    if(ItemStackUtil.getBlockPos(stack) != null) {
                        return true;
                    }
                }

                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != ModItems.BLOCK_GPS_RECORDER.get()) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        for(int slotIndex = 0; slotIndex < inventoryItems.getSlots(); slotIndex++) {
            ItemStack receiverCard = inventoryItems.getStackInSlot(slotIndex);
            if(receiverCard.isEmpty()) continue;
            updateReceiver(ItemStackUtil.getBlockPos(receiverCard));
        }
    }
}
