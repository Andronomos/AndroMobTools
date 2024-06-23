package andronomos.androtech.block.ExperienceAttractor;

import andronomos.androtech.Constants;
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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ExperienceAttractorMenu extends BaseMenu {
	public ExperienceAttractorMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public ExperienceAttractorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.EXPERIENCE_ATTRACTOR_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		setSlotIndexes(Constants.VANILLA_INVENTORY_SLOT_COUNT);
		addDataSlots(data);
	}

	@Override
	public boolean stillValid(@NotNull Player playerIn) {
		return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos()), player, BlockRegistry.EXPERIENCE_ATTRACTOR.get());
	}
}
