package andronomos.androtech.item.activatableItem;

import andronomos.androtech.item.activatableItem.AbstractActivatableItem;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.PlayerUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NaniteModule extends AbstractActivatableItem {
    public static final int ATTRACTOR_UNIT_DURABILITY = 1000;
    private int repairAmount = 5;

    public NaniteModule(Properties properties, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
    }

    public NaniteModule(Properties properties) {
        super(properties, true, true);
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
                    doDamage(stack, player, repairAmount, true);
                }
            }
        }
    }
}
