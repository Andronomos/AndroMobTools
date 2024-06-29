package andronomos.androtech.block.entityvacuum;

import andronomos.androtech.block.base.BaseMenu;
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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityVacuumMenu extends BaseMenu {
	public EntityVacuumMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public EntityVacuumMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.ENTITY_VACUUM_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof EntityVacuumBlockEntity entityVacuumBlockEntity) {
			entityVacuumBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
				addInventory(handler, 3, 7);
			});
		}
		setSlotIndexes(21);
		addDataSlots(data);
	}

	@Override
	public boolean stillValid(@NotNull Player playerIn) {
		return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos()), player, BlockRegistry.ENTITY_VACUUM.get());
	}
}
