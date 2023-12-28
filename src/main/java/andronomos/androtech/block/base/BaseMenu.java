package andronomos.androtech.block.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BaseMenu extends AbstractContainerMenu {
	private final Inventory inventory;
	private final Player player;

	protected BaseMenu(@Nullable MenuType<?> menuType, int id, Inventory inventory) {
		super(menuType, id);
		this.inventory = inventory;
		this.player = inventory.player;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int p_38942_) {
		return null;
	}

	@Override
	public boolean stillValid(Player player) {
		return false;
	}
}
