package andronomos.androtech.network;

import andronomos.androtech.AndroTech;
import andronomos.androtech.network.packet.*;
//import andronomos.androtech.network.packet.SyncRedstoneTransmitterState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class AndroTechPacketHandler {
	private static final String PROTOCOL_VERSION = "1";

	private static SimpleChannel INSTANCE;

	private static int packetId = 0;
	private static int id() {
		return packetId++;
	}

	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(AndroTech.MODID, "messages"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();

		INSTANCE = net;

		net.messageBuilder(PoweredStateSyncPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(PoweredStateSyncPacket::decode)
				.encoder(PoweredStateSyncPacket::encode)
				.consumerMainThread(PoweredStateSyncPacket::handle)
				.add();

		net.messageBuilder(TransmitterStateSyncPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(TransmitterStateSyncPacket::decode)
				.encoder(TransmitterStateSyncPacket::encode)
				.consumerMainThread(TransmitterStateSyncPacket::handle)
				.add();

		net.messageBuilder(EntityRepulsorOverlaySyncPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(EntityRepulsorOverlaySyncPacket::decode)
				.encoder(EntityRepulsorOverlaySyncPacket::encode)
				.consumerMainThread(EntityRepulsorOverlaySyncPacket::handle)
				.add();

		net.messageBuilder(MobKillerOverlaySyncPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(MobKillerOverlaySyncPacket::decode)
				.encoder(MobKillerOverlaySyncPacket::encode)
				.consumerMainThread(MobKillerOverlaySyncPacket::handle)
				.add();

		net.messageBuilder(EntityVacuumOverlaySyncPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
				.decoder(EntityVacuumOverlaySyncPacket::decode)
				.encoder(EntityVacuumOverlaySyncPacket::encode)
				.consumerMainThread(EntityVacuumOverlaySyncPacket::handle)
				.add();

		//net.messageBuilder(SyncMachineEnergy.class, id(), NetworkDirection.PLAY_TO_CLIENT)
		//		.decoder(SyncMachineEnergy::decode)
		//		.encoder(SyncMachineEnergy::encode)
		//		.consumerMainThread(SyncMachineEnergy::handle)
		//		.add();
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
