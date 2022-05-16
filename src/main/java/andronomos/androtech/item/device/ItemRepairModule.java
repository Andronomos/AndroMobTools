package andronomos.androtech.item.device;

import andronomos.androtech.item.device.base.AbstractTickingDevice;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.PlayerUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRepairModule extends AbstractTickingDevice {
    public static final int REPAIR_MODULE_RATE = 10;

    public ItemRepairModule(Properties properties, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
    }

    public ItemRepairModule(Properties properties) {
        super(properties, false, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (level.isClientSide || !isActivated(stack) || !(entity instanceof Player)) return;

        if(isBroken(stack)) {
            deactivate(stack, (Player)entity);
            return;
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
                currItem.setDamageValue(currItem.getDamageValue() - this.REPAIR_MODULE_RATE);
                if(this.takeDamage) {
                    doDamage(stack, player, REPAIR_MODULE_RATE, true);
                }
            }
        }
    }
}
