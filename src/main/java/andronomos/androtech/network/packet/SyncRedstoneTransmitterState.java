package andronomos.androtech.network.packet;

import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlock;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlockEntity;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncRedstoneTransmitterState {
	private final BlockPos pos;

	public SyncRedstoneTransmitterState(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(SyncRedstoneTransmitterState msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static SyncRedstoneTransmitterState decode(FriendlyByteBuf buf) {
		return new SyncRedstoneTransmitterState(buf.readBlockPos());
	}

	public static void handle(SyncRedstoneTransmitterState msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level.getBlockEntity(msg.pos) instanceof RedstoneSignalTransmitterBlockEntity) {
				level.getBlockEntity(msg.pos).getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
					BlockState transmitterState = level.getBlockState(msg.pos);
					for(int slotIndex = 0; slotIndex < RedstoneSignalTransmitterBlock.SLOTS; slotIndex++) {
						ItemStack blockGPSModule = itemHandler.getStackInSlot(slotIndex);
						if(!blockGPSModule.isEmpty()) {
							BlockPos receiverPos = ItemStackHelper.getBlockPos(blockGPSModule);
							BlockState receiverState = level.getBlockState(receiverPos);
							if(receiverState.getBlock() != BlockRegistry.REDSTONE_SIGNAL_RECEIVER.get()) return;
							if(transmitterState.getValue(BlockStateProperties.POWERED))
								level.setBlock(receiverPos, receiverState.setValue(BlockStateProperties.POWERED, true), 3);
							else
								level.setBlock(receiverPos, receiverState.setValue(BlockStateProperties.POWERED, false), 3);
						}
					}
				});
			}
		});

		ctx.get().setPacketHandled(true);
	}
}
