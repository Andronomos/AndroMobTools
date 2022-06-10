package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.BaseContainerMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AnimalFarmerContainer extends BaseContainerMenu {
	public final BlockEntity blockEntity;

	public AnimalFarmerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.ANIMAL_FARMER.get(), windowId, inventory);

		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

		if(blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for (int i = 0; i < 6; i++) {
					for(int j = 0; j < 9; j++) {
						addSlot(new SlotItemHandler(h, j + i * 9, Const.CONTAINER_GENERIC_SLOT_X_OFFSET + j * Const.SCREEN_SLOT_SIZE, Const.SCREEN_SLOT_SIZE + i * Const.SCREEN_SLOT_SIZE));
					}
				}
			});
		}

		layoutPlayerInventorySlots(8, 140);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.ANIMAL_FARMER.get());
	}
}
