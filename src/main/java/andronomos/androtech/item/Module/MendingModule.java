package andronomos.androtech.item.Module;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.TickingDevice;
import andronomos.androtech.util.ItemStackUtils;
import andronomos.androtech.util.PlayerUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MendingModule extends TickingDevice {
	private final int repairRate = AndroTechConfig.MENDING_MODULE_REPAIR_RATE.get();

	public MendingModule(Properties properties) {
		super(properties, AndroTechConfig.MOB_STASIS_MODULE_TAKE_DAMAGE.get());
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.MENDING_MODULE_DURABILITY.get();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !isActivated(stack) || !(entity instanceof Player)) return;

		if(isBroken(stack)) {
			deactivate(stack);
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
		for(NonNullList<ItemStack> playersItems : PlayerUtils.GetAllInventories(player)) {
			for(int i = 0; i < playersItems.size(); ++i) {
				ItemStack currItem = playersItems.get(i);
				if(!ItemStackUtils.isRepairable(currItem)) continue;
				currItem.setDamageValue(currItem.getDamageValue() - repairRate);
				if(this.takeDamage) {
					doDamage(stack, player, repairRate, true);
				}
			}
		}
	}
}
