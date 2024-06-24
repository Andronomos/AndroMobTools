package andronomos.androtech.network.packet;

import andronomos.androtech.block.itemattractor.ItemAttractorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ItemAttractorOverlaySyncPacket {
	private final BlockPos pos;

	public ItemAttractorOverlaySyncPacket(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(ItemAttractorOverlaySyncPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static ItemAttractorOverlaySyncPacket decode(FriendlyByteBuf buf) {
		return new ItemAttractorOverlaySyncPacket(buf.readBlockPos());
	}

	public static void handle(ItemAttractorOverlaySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = Objects.requireNonNull(ctx.get().getSender()).level();
			ItemAttractorBlockEntity blockEntity = (ItemAttractorBlockEntity)level.getBlockEntity(msg.pos);

			if(blockEntity != null) {
				blockEntity.toggleRenderBox();
				BlockState state = level.getBlockState(msg.pos);
				level.setBlockAndUpdate(msg.pos, state);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
