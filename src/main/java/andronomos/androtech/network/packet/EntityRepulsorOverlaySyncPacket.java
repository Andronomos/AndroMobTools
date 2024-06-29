package andronomos.androtech.network.packet;

import andronomos.androtech.block.entityrepulsor.EntityRepulsorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class EntityRepulsorOverlaySyncPacket {
	private final BlockPos pos;

	public EntityRepulsorOverlaySyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(EntityRepulsorOverlaySyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static EntityRepulsorOverlaySyncPacket decode(FriendlyByteBuf buf) {
		return new EntityRepulsorOverlaySyncPacket(buf.readBlockPos());
	}

	public static void handle(EntityRepulsorOverlaySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			EntityRepulsorBlockEntity repulsor = (EntityRepulsorBlockEntity)level.getBlockEntity(msg.pos);

			if(repulsor != null) {
				repulsor.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
