package andronomos.androtech.gui;

import andronomos.androtech.AndroTech;
import andronomos.androtech.inventory.CropFarmerContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CropFarmerScreen extends BaseScreen<CropFarmerContainer> {
	public static final ResourceLocation CROP_FARMER_GUI = new ResourceLocation(AndroTech.MOD_ID, "textures/gui/machine/crop_farmer.png");

	public CropFarmerScreen(CropFarmerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.imageHeight = 222;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int i, int j) {
		this.drawName(stack, title.getString());
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, INVENTORY_LARGE);
	}
}
