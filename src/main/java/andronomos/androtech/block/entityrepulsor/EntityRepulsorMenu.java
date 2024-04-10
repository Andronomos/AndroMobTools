package andronomos.androtech.block.entityrepulsor;

import andronomos.androtech.block.BaseMenu;
import andronomos.androtech.block.damagepad.DamagePadBlock;
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
	public EntityRepulsorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public EntityRepulsorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.ENTITY_REPULSOR_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof EntityRepulsorBlockEntity entityRepulsorBlockEntity) {
			entityRepulsorBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
				//todo: replace augments with repulsor upgrades
				addSlot(new RestrictedSlotHandler(itemHandler, 0, 56, 30, ItemRegistry.SHARPNESS_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 1, 81, 30, ItemRegistry.LOOTING_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 2, 106, 30, ItemRegistry.FIRE_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				//addSlot(new RestrictedSlotHandler(itemHandler, 3, 118, 30, ItemRegistry.SMITE_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
			});
		}
		setSlotIndexes(DamagePadBlock.SLOTS);
		//addDataSlots(data);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, blockEntity.getBlockPos()), this.player, BlockRegistry.ENTITY_REPULSOR.get());
	}
}
