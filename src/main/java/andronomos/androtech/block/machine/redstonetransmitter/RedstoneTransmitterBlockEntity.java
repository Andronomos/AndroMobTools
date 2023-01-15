package andronomos.androtech.block.machine.redstonetransmitter;

import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.block.machine.MachineSlotItemStackHandler;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class RedstoneTransmitterBlockEntity extends MachineBlockEntity implements MenuProvider {
	public static final int SLOTS = 9;

    public RedstoneTransmitterBlockEntity(BlockPos pos, BlockState state) {
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
        return new MachineSlotItemStackHandler(this, ModItems.BLOCK_GPS_MODULE.get(), RedstoneTransmitterBlockEntity.SLOTS) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(stack.getItem() == this.allowedItem) {
                    return ItemStackUtils.getBlockPos(stack) != null;
                }

                return false;
            }
        };
    }

    @Override
    protected ModEnergyStorage createEnergyHandler() {
        return null;
    }

    @Override
    public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        for(int slotIndex = 0; slotIndex < itemHandler.getSlots(); slotIndex++) {
            ItemStack receiverCard = itemHandler.getStackInSlot(slotIndex);
            if(receiverCard.isEmpty()) continue;
            updateReceiver(ItemStackUtils.getBlockPos(receiverCard));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(RedstoneTransmitter.DISPLAY_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RedstoneTransmitterMenu(pContainerId, pPlayerInventory, this);
    }
}
