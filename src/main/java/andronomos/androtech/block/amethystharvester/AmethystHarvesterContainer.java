package andronomos.androtech.block.amethystharvester;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.BaseContainerMenu;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AmethystHarvesterContainer extends BaseContainerMenu {
	public AmethystHarvesterBE blockEntity;

	protected AmethystHarvesterContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.AMETHYST_HARVESTER.get(), windowId, inventory);

		BlockEntity blockEntityAtPos = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntityAtPos != null && blockEntityAtPos instanceof AmethystHarvesterBE amethystHarvester) {
			blockEntity = amethystHarvester;

			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addMachineInventory(h);
			});

			addSlot(new SlotItemHandler(blockEntity.pickaxeSlot, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET, 16));
		}

		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_X);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return false;
	}
}
