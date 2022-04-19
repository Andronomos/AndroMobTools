package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class RedstoneTransmitterContainer extends BaseContainerMenu {
	private BlockEntity blockEntity;

	public RedstoneTransmitterContainer(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
		super(ModContainers.REDSTONE_TRANSMITTER.get(), windowId);
		blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
		playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);

		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for(int s = 0; s < 9; s++) {
					addSlot(new SlotItemHandler(h, s, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * s, 27));
				}
			});
		}

		layoutPlayerInventorySlots(8, 84);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, ModBlocks.REDSTONE_TRANSMITTER.get());
	}
}
