package andronomos.androtech.block.machine.itemincinerator;

import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.block.machine.itemattractor.ItemAttractor;
import andronomos.androtech.block.machine.itemattractor.ItemAttractorMenu;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemIncineratorBlockEntity extends MachineBlockEntity implements MenuProvider {
    public static final int SLOTS = 1;

    public ItemIncineratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_INCENERATOR.get(), pos, state);
    }

    @Override
    protected ItemStackHandler createInventoryItemHandler() {
        return new ItemStackHandler(1) {

            @Override
            public void setStackInSlot(int slot, @Nonnull ItemStack stack){
            }

            @Nonnull
            @Override
            public ItemStack getStackInSlot(int slot){
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate){
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(ItemIncinerator.DISPLAY_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ItemIncineratorMenu(pContainerId, pPlayerInventory, this);
    }
}
