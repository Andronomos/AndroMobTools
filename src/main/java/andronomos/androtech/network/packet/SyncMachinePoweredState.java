package andronomos.androtech.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMachinePoweredState {
	private final BlockPos pos;

	public SyncMachinePoweredState(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(SyncMachinePoweredState msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static SyncMachinePoweredState decode(FriendlyByteBuf buf) {
		return new SyncMachinePoweredState(buf.readBlockPos());
	}

	public static void handle(SyncMachinePoweredState msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level == null) return;
			BlockState state = level.getBlockState(msg.pos);
			level.setBlockAndUpdate(msg.pos, state.cycle(BlockStateProperties.POWERED));
		});

		ctx.get().setPacketHandled(true);
	}
}
