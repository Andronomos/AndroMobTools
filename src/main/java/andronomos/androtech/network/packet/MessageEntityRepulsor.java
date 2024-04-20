package andronomos.androtech.network.packet;

import andronomos.androtech.block.mobrepulsor.MobRepulsorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageEntityRepulsor {
	private final BlockPos pos;

	public MessageEntityRepulsor(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(MessageEntityRepulsor msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static MessageEntityRepulsor decode(FriendlyByteBuf buf) {
		return new MessageEntityRepulsor(buf.readBlockPos());
	}

	public static void handle(MessageEntityRepulsor msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level == null) return;

			MobRepulsorBlockEntity repulsor = (MobRepulsorBlockEntity)level.getBlockEntity(msg.pos);

			if(repulsor != null) {
				repulsor.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
