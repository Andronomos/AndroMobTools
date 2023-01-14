package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.OverlayButton;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CropFarmerScreen extends MachineScreen<CropFarmerMenu> {
	private PowerButton powerButton;
	private OverlayButton overlayButton;

	public CropFarmerScreen(CropFarmerMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
		this.imageHeight = Const.SCREEN_LARGE_IMAGE_HEIGHT;
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.INSTANCE.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));

		//overlayButton = (OverlayButton) this.addButton(new OverlayButton((button) -> {
		//
		//}, menu.blockEntity));
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.SINGLE_SLOT_MACHINE_SCREEN);
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
