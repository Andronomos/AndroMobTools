package andronomos.androtech.network.packet;

import andronomos.androtech.block.itemattractor.EntityVacuumBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class EntityVacuumOverlaySyncPacket {
	private final BlockPos pos;

	public EntityVacuumOverlaySyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(EntityVacuumOverlaySyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static EntityVacuumOverlaySyncPacket decode(FriendlyByteBuf buf) {
		return new EntityVacuumOverlaySyncPacket(buf.readBlockPos());
	}

	public static void handle(EntityVacuumOverlaySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			EntityVacuumBlockEntity blockEntity = (EntityVacuumBlockEntity)level.getBlockEntity(msg.pos);

			if(blockEntity != null) {
				blockEntity.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
