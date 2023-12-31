package andronomos.androtech.block.itemattractor;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.constants.SlotConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemAttractorScreen extends BaseScreen<ItemAttractorMenu> {
	//private PowerButton powerButton;

	public ItemAttractorScreen(ItemAttractorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		this.imageHeight = SlotConstants.SCREEN_LARGE_IMAGE_HEIGHT;
	}

	@Override
	protected void init() {
		super.init();

		//powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
		//	AndroTechPacketHandler.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		//}, menu.blockEntity));
	}

	//@Override
	//public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
	//	this.renderBackground(stack);
	//	super.render(stack, mouseX, mouseY, partialTicks);
	//	this.renderTooltip(stack, mouseX, mouseY);
	//}

	//@Override
	//protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
	//	//this.drawButtonTooltips(stack, mouseX, mouseY);
	//	this.drawName(stack, title.getString());
	//	powerButton.update();
	//}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/item_attractor.png"));
	}
}
