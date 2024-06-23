package andronomos.androtech.network.packet;

import andronomos.androtech.block.ExperienceAttractor.ExperienceAttractorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ExperienceAttractorOverlaySyncPacket {
	private final BlockPos pos;

	public ExperienceAttractorOverlaySyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(ExperienceAttractorOverlaySyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static ExperienceAttractorOverlaySyncPacket decode(FriendlyByteBuf buf) {
		return new ExperienceAttractorOverlaySyncPacket(buf.readBlockPos());
	}

	public static void handle(ExperienceAttractorOverlaySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			ExperienceAttractorBlockEntity blockEntity = (ExperienceAttractorBlockEntity)level.getBlockEntity(msg.pos);

			if(blockEntity != null) {
				blockEntity.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
