package andronomos.androtech.block.machine.itemincinerator;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.block.machine.itemattractor.ItemAttractorBlockEntity;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class ItemIncineratorMenu extends MachineMenu {
    public ItemIncineratorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public ItemIncineratorMenu(int id, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.ITEM_INCINERATOR.get(), id, inv);

        if(entity != null && entity instanceof ItemIncineratorBlockEntity itemIncineratorBlockEntity) {
            blockEntity = itemIncineratorBlockEntity;
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0,80, 30));
            });
        }

        setupSlotIndexs(ItemIncineratorBlockEntity.SLOTS);
        layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.ITEM_INCINERATOR.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        final Slot slot = this.slots.get(slotIndex);
        if(slotIndex > 0 && this.getSlot(0).mayPlace(slot.getItem())) {
            slot.set(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
