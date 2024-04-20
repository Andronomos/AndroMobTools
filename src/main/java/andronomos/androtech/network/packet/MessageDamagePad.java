package andronomos.androtech.network.packet;

import andronomos.androtech.block.mobkiller.MobKillerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageDamagePad {
	private final BlockPos pos;

	public MessageDamagePad(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(MessageDamagePad msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static MessageDamagePad decode(FriendlyByteBuf buf) {
		return new MessageDamagePad(buf.readBlockPos());
	}

	public static void handle(MessageDamagePad msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level == null) return;

			MobKillerBlockEntity damagePad = (MobKillerBlockEntity)level.getBlockEntity(msg.pos);

			if(damagePad != null) {
				damagePad.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
