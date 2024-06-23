package andronomos.androtech.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class PoweredStateSyncPacket {
	private final BlockPos pos;

	public PoweredStateSyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(PoweredStateSyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static PoweredStateSyncPacket decode(FriendlyByteBuf buf) {
		return new PoweredStateSyncPacket(buf.readBlockPos());
	}

	public static void handle(PoweredStateSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			BlockState state = level.getBlockState(msg.pos);
			level.setBlockAndUpdate(msg.pos, state.cycle(BlockStateProperties.POWERED));
		});
		ctx.get().setPacketHandled(true);
	}
}
