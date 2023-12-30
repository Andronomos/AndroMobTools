package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.block.base.BaseMenu;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DamagePadMenu extends BaseMenu {
	public DamagePadMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
	}

	public DamagePadMenu(int id, Inventory inventory, BlockEntity entity) {
		super(MenuTypeRegistry.MOB_KILLING_PAD.get(), id, inventory);
	}
}
