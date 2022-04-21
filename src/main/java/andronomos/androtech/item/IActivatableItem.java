package andronomos.androtech.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IActivatableItem {
	boolean isBroken(ItemStack stack);
	boolean isActivated(ItemStack stack);
	void activate(ItemStack stack, Player player);
	void deactivate(ItemStack stack, Player player);
}
