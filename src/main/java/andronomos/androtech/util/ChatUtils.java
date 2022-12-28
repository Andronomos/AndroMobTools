package andronomos.androtech.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChatUtils {
	public static String blockPosToString(BlockPos pos) {
		return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
	}

	public static void sendStatusMessage(Player player, String message) {
		player.displayClientMessage(Component.translatable(message), true);
	}

	public static void sendStatusMessage(Player player, Component nameTextComponent) {
		if (player.level.isClientSide) {
			player.displayClientMessage(nameTextComponent, true);
		}
	}
}
