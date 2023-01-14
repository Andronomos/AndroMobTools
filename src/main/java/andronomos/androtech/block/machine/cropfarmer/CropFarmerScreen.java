package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.block.machine.cropfarmer.renderer.EnergyInfoArea;
import andronomos.androtech.gui.widget.button.sidebutton.OverlayButton;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import andronomos.androtech.util.MouseUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class CropFarmerScreen extends MachineScreen<CropFarmerMenu> {
	private PowerButton powerButton;
	private OverlayButton overlayButton;
	private EnergyInfoArea energyInfoArea;

	public CropFarmerScreen(CropFarmerMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
		this.imageHeight = Const.SCREEN_LARGE_IMAGE_HEIGHT;
		assignEnergyInfoArea();
	}

	private void assignEnergyInfoArea() {
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;

		energyInfoArea = new EnergyInfoArea(relX + 156, relY + 13, menu.blockEntity.getEnergyStorage());
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));

		//overlayButton = (OverlayButton) this.addButton(new OverlayButton((button) -> {
		//
		//}, menu.blockEntity));
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.MACHINE_SCREEN);
		energyInfoArea.draw(stack);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		this.drawButtonTooltips(stack, mouseX, mouseY);
		this.drawName(stack, title.getString());
		powerButton.update();


		renderEnergyAreaTooltips(stack, mouseX, mouseY);
	}

	private void renderEnergyAreaTooltips(PoseStack stack, int mouseX, int mouseY) {
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;

		if(isMouseAboveArea(mouseX, mouseY, relX, relY, 156, 13, 8, 64)) {
			renderTooltip(stack, energyInfoArea.getTooltips(),
					Optional.empty(), mouseX - relX, mouseY - relY);
		}
	}

	private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
		return MouseUtils.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
	}

	//@Override
	//public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
	//	AndroTech.LOGGER.info("CropFarmerScreen#keyPressed | pKeyCode >> {}", pKeyCode);
	//
	//	//96 - `
	//	//258 - tab
	//
	//	if(pKeyCode == 96) {
	//		LevelUtils.displayWorkArea(menu.blockEntity.getWorkArea(), menu.blockEntity.getLevel(), Blocks.AIR);
	//	} else if(pKeyCode == 258) {
	//		LevelUtils.displayWorkArea(menu.blockEntity.getWorkArea(), menu.blockEntity.getLevel(), ModBlocks.OVERLAY.get());
	//	}
	//
	//	return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	//}

	//@Override
	//public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
	//	AndroTech.LOGGER.info("CropFarmerScreen#mouseClicked | pButton >> {}", pButton);
	//
	//	//0 - m1
	//	//1 - m2
	//	//2 - m3
	//
	//	return super.mouseClicked(pMouseX, pMouseY, pButton);
	//}
}
