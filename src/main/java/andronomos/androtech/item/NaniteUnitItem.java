package andronomos.androtech.item;

import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.PlayerUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NaniteUnitItem extends AbstractActivatableItem {
    private int repairAmount = 5;

    public NaniteUnitItem(Properties properties, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
    }

    public NaniteUnitItem(Properties properties) {
        super(properties.setNoRepair(), true, true);
    }

    @Override
    public InteractionResultHolder use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!level.isClientSide) {
            if(!isActivated(stack) && !isBroken(stack)) {
                activate(stack, player);
            } else {
                deactivate(stack, player);
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (level.isClientSide || !isActivated(stack) || !(entity instanceof Player)) return;

        if(this.takeDamage) {
            if(isBroken(stack)) {
                deactivate(stack, (Player)entity);
                return;
            }
        }

        if(this.tickCounter == this.tickDelay) {
            this.tickCounter = 0;
            Player living = (Player) entity;
            RepairPlayersInventory(stack, living);
        }

        this.tickCounter++;
    }

    private void RepairPlayersInventory(ItemStack stack, Player player) {
        for(NonNullList<ItemStack> nonnulllist : PlayerUtil.GetAllInventories(player)) {
            for(int i = 0; i < nonnulllist.size(); ++i) {
                ItemStack currItem = nonnulllist.get(i);
                if(!ItemStackUtil.isRepairable(currItem)) continue;
                currItem.setDamageValue(currItem.getDamageValue() - this.repairAmount);
                if(this.takeDamage) {
                    doDamage(stack, player, true);
                }
            }
        }
    }
}