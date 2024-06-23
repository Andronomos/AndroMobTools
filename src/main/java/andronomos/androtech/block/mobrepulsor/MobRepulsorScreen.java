package andronomos.androtech.block.mobrepulsor;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.inventory.client.RenderOutlineButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.EntityRepulsorOverlaySyncPacket;
import andronomos.androtech.network.packet.PoweredStateSyncPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MobRepulsorScreen extends BaseScreen<MobRepulsorMenu> {
	private PowerButton powerButton;
	private RenderOutlineButton overlayButton;
	private final MobRepulsorBlockEntity entity;

	public MobRepulsorScreen(MobRepulsorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		entity = menu.repulsor;
	}

	@Override
	protected void init() {
		super.init();
		powerButton = (PowerButton)this.addButton(new PowerButton((button) ->
				AndroTechPacketHandler.sendToServer(new PoweredStateSyncPacket(entity.getBlockPos())), menu.blockEntity));

		overlayButton = (RenderOutlineButton)this.addButton(new RenderOutlineButton((button) -> {
			AndroTechPacketHandler.sendToServer(new EntityRepulsorOverlaySyncPacket(entity.getBlockPos()));
			entity.showRenderBox = !entity.showRenderBox;
		}));
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
		powerButton.update();
		overlayButton.update(entity.showRenderBox);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/mob_repulsor.png"));
	}
}
