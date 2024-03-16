package andronomos.androtech.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChatHelper {
	public static void sendStatusMessage(Player player, String message) {
		sendStatusMessage(player, Component.translatable(message));
	}

	public static void sendStatusMessage(Player player, Component nameTextComponent) {
		if (player.level().isClientSide) {
			player.displayClientMessage(nameTextComponent, true);
		}
	}
}
