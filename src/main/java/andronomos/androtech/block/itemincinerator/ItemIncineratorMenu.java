package andronomos.androtech.block.itemincinerator;

import andronomos.androtech.block.BaseMenu;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemIncineratorMenu extends BaseMenu {
    public ItemIncineratorMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
    }

    public ItemIncineratorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(MenuTypeRegistry.ITEM_INCINERATOR_MENU.get(), containerId, inventory, entity, data);
        addPlayerInventory();
        addPlayerHotbar();
        if(entity instanceof ItemIncineratorBlockEntity itemIncineratorBlockEntity) {
            itemIncineratorBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0,80, 30));
            });
        }
        setSlotIndexes(ItemIncineratorBlock.SLOTS);
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos()), player, BlockRegistry.ITEM_INCINERATOR.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        final Slot slot = this.slots.get(slotIndex);
        if(slotIndex > 0 && this.getSlot(0).mayPlace(slot.getItem())) {
            slot.set(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
