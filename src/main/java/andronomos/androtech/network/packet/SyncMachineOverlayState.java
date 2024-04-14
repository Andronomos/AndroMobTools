package andronomos.androtech.network.packet;

import andronomos.androtech.block.entityrepulsor.EntityRepulsorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMachineOverlayState {
	private final BlockPos pos;

	public SyncMachineOverlayState(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(SyncMachineOverlayState msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static SyncMachineOverlayState decode(FriendlyByteBuf buf) {
		return new SyncMachineOverlayState(buf.readBlockPos());
	}

	public static void handle(SyncMachineOverlayState msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level();
			if(level == null) return;

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