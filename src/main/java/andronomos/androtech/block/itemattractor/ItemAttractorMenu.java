package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.BaseMenu;
import andronomos.androtech.Constants;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ItemAttractorMenu extends BaseMenu {
	public ItemAttractorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public ItemAttractorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.ITEM_ATTRACTOR_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof ItemAttractorBlockEntity itemAttractorBlockEntity) {
			itemAttractorBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
				addInventory(h);
			});
		}
		setSlotIndexes(Constants.VANILLA_INVENTORY_SLOT_COUNT);
		addDataSlots(data);
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, BlockRegistry.ITEM_ATTRACTOR.get());
	}
}
