package andronomos.androtech.block.wirelessredstone.redstonetransmitter;

import andronomos.androtech.base.BaseBlockEntity;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RedstoneSignalTransmitterBlockEntity extends BaseBlockEntity implements MenuProvider {
	private BlockPos lastReceiverPosition;

	public RedstoneSignalTransmitterBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.REDSTONE_SIGNAL_TRANSMITTER_BE.get(), pos, state, new SimpleContainerData(RedstoneSignalTransmitterBlock.SLOTS));
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(RedstoneSignalTransmitterBlock.SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(RedstoneSignalTransmitterBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
		return new RedstoneSignalTransmitterMenu(containerId, pPlayerInventory, this, this.data);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		ItemStack receiverCard = itemHandler.getStackInSlot(0);

		if(receiverCard.isEmpty()) {
			BlockState receiverState = getReceiverState(lastReceiverPosition);
			setReceiverPoweredState(lastReceiverPosition, receiverState, false);
		}

		BlockPos receiverPos = ItemStackHelper.getBlockPos(receiverCard);
		lastReceiverPosition = receiverPos;
		updateReceiver(receiverPos);
	}

	private BlockState getReceiverState(BlockPos receiverPos) {
		if(receiverPos == null) {
			return null;
		}
		return Objects.requireNonNull(level).getBlockState(receiverPos);
	}

	private void updateReceiver(BlockPos receiverPos) {
		if(receiverPos == null) {
			return;
		}

		BlockState receiverState = getReceiverState(receiverPos);

		if(receiverState == null) {
			return;
		}

		if(receiverState.getBlock() != BlockRegistry.REDSTONE_SIGNAL_RECEIVER.get()) {
			return;
		}

		boolean receiverIsPowered = receiverState.getValue(POWERED);
		boolean transmitterIsPowered = Objects.requireNonNull(level).getBlockState(this.worldPosition).getValue(POWERED);

		if(receiverIsPowered != transmitterIsPowered) {
			setReceiverPoweredState(receiverPos, receiverState, transmitterIsPowered);
		}
	}

	private void setReceiverPoweredState(BlockPos receiverPos, BlockState receiverState, boolean isPowered) {
		if(receiverPos == null || receiverState == null) {
			return;
		}
		if (level != null) {
			level.setBlockAndUpdate(receiverPos, receiverState.setValue(POWERED, isPowered));
		}
	}
}
