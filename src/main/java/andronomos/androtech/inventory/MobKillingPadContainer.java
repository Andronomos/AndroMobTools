package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.MobKillingPadBE;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import andronomos.androtech.util.EnchantmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MobKillingPadContainer extends BaseContainerMenu {
    private BlockEntity blockEntity;

    public MobKillingPadContainer(int windowId, BlockPos pos, Inventory inventory) {
        super(ModContainers.MOB_KILLING_PAD.get(), windowId, inventory);

        blockEntity =  player.getCommandSenderWorld().getBlockEntity(pos);

        if(blockEntity != null && blockEntity instanceof MobKillingPadBE) {
            final MobKillingPadBE entity = (MobKillingPadBE) blockEntity;
            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
                addSlot(new SlotItemHandler(itemHandler, 0, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 3, 30));
                addSlot(new SlotItemHandler(itemHandler, 1, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 5, 30));
            });
        }

        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.MOB_KILLING_PAD.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerEntity, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            returnStack = stack.copy();

            if (index == 0 || index == 1) {
                //attempt to the move the stack to any slot between slot 2 (inventory begin) and slot 37 (hotbar end) (slot 0 and 1 are the mob grinder inventory)
                if (!this.moveItemStackTo(stack, 2, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, returnStack);
            } else {
                if(stack.getItem() == Items.ENCHANTED_BOOK) {
                    if(EnchantmentUtil.hasEnchantment(Enchantments.MOB_LOOTING, stack)) {
                        //attempt to move the stack to slot 0.
                        if (!this.moveItemStackTo(stack, 0, 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (EnchantmentUtil.hasEnchantment(Enchantments.FIRE_ASPECT, stack)) {
                        //attempt to move the stack to slot 1.
                        if (!this.moveItemStackTo(stack, 1, 2, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (index < 28) {
                    //attempt to move the stack to any slot between slot 28 and 37 (the hotbar)
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 2, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerEntity, stack);
        }

        return returnStack;
    }
}
