package andronomos.androtech.network.packet;

import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitterBlockEntity;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.util.ItemStackUtils;
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
		BlockPos pos = new BlockPos(buf.readBlockPos());
		return new SyncRedstoneTransmitterState(pos);
	}

	public static void handle(SyncRedstoneTransmitterState msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			Level level = ctx.get().getSender().getLevel();

			if(level.getBlockEntity(msg.pos) instanceof RedstoneTransmitterBlockEntity) {
				level.getBlockEntity(msg.pos).getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
					BlockState transmitterState = level.getBlockState(msg.pos);
					for(int slotIndex = 0; slotIndex < 9; slotIndex++) {
						ItemStack blockGPSModule = itemHandler.getStackInSlot(slotIndex);
						if(!blockGPSModule.isEmpty()) {
							BlockPos receiverPos = ItemStackUtils.getBlockPos(blockGPSModule);
							BlockState receiverState = level.getBlockState(receiverPos);
							if(receiverState.getBlock() != ModBlocks.REDSTONE_RECEIVER.get()) return;
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
