package andronomos.androtech.block.entityrepulsor;

import andronomos.androtech.block.base.BaseMenu;
import andronomos.androtech.inventory.server.RestrictedSlotHandler;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class EntityRepulsorMenu extends BaseMenu {
	public EntityRepulsorBlockEntity repulsor;
	private static final int UPGRADE_STACK_LIMIT = 3;

	public EntityRepulsorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public EntityRepulsorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.ENTITY_REPULSOR_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof EntityRepulsorBlockEntity entityRepulsorBlockEntity) {
			repulsor = entityRepulsorBlockEntity;
			entityRepulsorBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
				addSlot(new RestrictedSlotHandler(itemHandler, 0, 56, 30, ItemRegistry.REPULSOR_WIDTH_UPGRADE.get().getDefaultInstance(), UPGRADE_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 1, 81, 30, ItemRegistry.REPULSOR_HEIGHT_UPGRADE.get().getDefaultInstance(), UPGRADE_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 2, 106, 30, ItemRegistry.REPULSOR_DISTANCE_UPGRADE.get().getDefaultInstance(), UPGRADE_STACK_LIMIT));
			});
		}
		setSlotIndexes(EntityRepulsorBlock.SLOTS);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, blockEntity.getBlockPos()), this.player, BlockRegistry.ENTITY_REPULSOR.get());
	}
}
