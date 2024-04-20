package andronomos.androtech.network.packet;

import andronomos.androtech.block.mobkiller.MobKillerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageMobKiller {
	private final BlockPos pos;

	public MessageMobKiller(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(MessageMobKiller msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static MessageMobKiller decode(FriendlyByteBuf buf) {
		return new MessageMobKiller(buf.readBlockPos());
	}

	public static void handle(MessageMobKiller msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level == null) return;

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
