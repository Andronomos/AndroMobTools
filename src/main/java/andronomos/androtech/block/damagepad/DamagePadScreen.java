package andronomos.androtech.block.damagepad;

import andronomos.androtech.AndroTech;
import andronomos.androtech.base.BaseScreen;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.inventory.client.RenderOutlineButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.MessageEntityRepulsor;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DamagePadScreen extends BaseScreen<DamagePadMenu> {
	private PowerButton powerButton;
	private RenderOutlineButton overlayButton;
	private final DamagePadBlockEntity entity;

	public DamagePadScreen(DamagePadMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		entity = menu.damagePad;
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton)this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));

		overlayButton = (RenderOutlineButton)this.addButton(new RenderOutlineButton((button) -> {
			AndroTechPacketHandler.sendToServer(new MessageEntityRepulsor(entity.getBlockPos()));
			entity.showRenderBox = !entity.showRenderBox;
		}));
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
		powerButton.update();
		overlayButton.update(entity.showRenderBox);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/damage_pad.png"));
	}
}
