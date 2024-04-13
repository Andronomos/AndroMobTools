package andronomos.androtech.block.wirelessredstone.redstonetransmitter;

import andronomos.androtech.AndroTech;
import andronomos.androtech.base.BaseScreen;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RedstoneSignalTransmitterScreen extends BaseScreen<RedstoneSignalTransmitterMenu> {
	private PowerButton powerButton;

	public RedstoneSignalTransmitterScreen(RedstoneSignalTransmitterMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		powerButton = (PowerButton)this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
		powerButton.update();
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/redstone_signal_transmitter.png"));
	}
}
