package andronomos.androtech.network;

import andronomos.androtech.network.packet.SyncMachineEnergy;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
//import andronomos.androtech.network.packet.SyncRedstoneTransmitterState;
import andronomos.androtech.network.packet.SyncRedstoneTransmitterState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class AndroTechPacketHandler {
	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void register() {
		int messageId = 0;

		INSTANCE.registerMessage(messageId++,
				SyncMachinePoweredState.class,
				SyncMachinePoweredState::encode,
				SyncMachinePoweredState::decode,
				SyncMachinePoweredState::handle);

		INSTANCE.registerMessage(messageId++,
				SyncRedstoneTransmitterState.class,
				SyncRedstoneTransmitterState::encode,
				SyncRedstoneTransmitterState::decode,
				SyncRedstoneTransmitterState::handle);

		INSTANCE.registerMessage(messageId++,
				SyncMachineEnergy.class,
				SyncMachineEnergy::encode,
				SyncMachineEnergy::decode,
				SyncMachineEnergy::handle);
	}

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public static <MSG> void sendToClients(MSG message) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}
}
