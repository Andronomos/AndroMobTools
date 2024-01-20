package andronomos.androtech.block.wirelessredstone.redstonetransmitter;

import andronomos.androtech.block.BaseBlockEntity;
import andronomos.androtech.block.damagepad.DamagePadBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class RedstoneSignalTransmitterBlockEntity extends BaseBlockEntity implements MenuProvider {
	public RedstoneSignalTransmitterBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.REDSTONE_SIGNAL_TRANSMITTER_BE.get(), pos, state, new SimpleContainerData(DamagePadBlock.SLOTS));
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(RedstoneSignalTransmitterBlock.SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(RedstoneSignalTransmitterBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory pPlayerInventory, Player pPlayer) {
		return new RedstoneSignalTransmitterMenu(containerId, pPlayerInventory, this, this.data);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		for(int slotIndex = 0; slotIndex < itemHandler.getSlots(); slotIndex++) {
			ItemStack receiverCard = itemHandler.getStackInSlot(slotIndex);
			if(receiverCard.isEmpty()) continue;
			updateReceiver(ItemStackHelper.getBlockPos(receiverCard));
		}
	}

	private void updateReceiver(BlockPos receiverPos) {
		if(receiverPos == null) return; //this should only happen when the receiver card doesn't have any coords
		BlockState receiverState = level.getBlockState(receiverPos);
		if(receiverState == null) return;
		if(receiverState.getBlock() != BlockRegistry.REDSTONE_SIGNAL_RECEIVER.get()) return;
		boolean receiverIsPowered = receiverState.getValue(POWERED);
		boolean transmitterIsPowered = level.getBlockState(this.worldPosition).getValue(POWERED);
		if(receiverIsPowered != transmitterIsPowered) {
			level.setBlockAndUpdate(receiverPos, receiverState.setValue(POWERED, transmitterIsPowered));
		}
	}
}
