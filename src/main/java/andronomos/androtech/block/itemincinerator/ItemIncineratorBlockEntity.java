package andronomos.androtech.block.itemincinerator;

import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemIncineratorBlockEntity extends BaseBlockEntity implements MenuProvider {
    public ItemIncineratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ITEM_INCINERATOR_BE.get(), pos, state, new SimpleContainerData(ItemIncineratorBlock.SLOTS));
    }

    @Nonnull
    protected ItemStackHandler createInventoryItemHandler() {
        return new ItemStackHandler(ItemIncineratorBlock.SLOTS) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(ItemIncineratorBlock.DISPLAY_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new ItemIncineratorMenu(containerId, playerInventory, this, this.data);
    }
}
