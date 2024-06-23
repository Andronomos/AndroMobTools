package andronomos.androtech.network.packet;

import andronomos.androtech.block.mobkiller.MobKillerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class MobKillerOverlaySyncPacket {
	private final BlockPos pos;

	public MobKillerOverlaySyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(MobKillerOverlaySyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static MobKillerOverlaySyncPacket decode(FriendlyByteBuf buf) {
		return new MobKillerOverlaySyncPacket(buf.readBlockPos());
	}

	public static void handle(MobKillerOverlaySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			MobKillerBlockEntity mobKiller = (MobKillerBlockEntity)level.getBlockEntity(msg.pos);

			if(mobKiller != null) {
				mobKiller.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
