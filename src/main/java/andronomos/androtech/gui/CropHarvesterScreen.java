package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.CropHarvesterContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CropHarvesterScreen extends BaseScreen<CropHarvesterContainer> {
	public CropHarvesterScreen(CropHarvesterContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.imageHeight = 114 + 6 * 18;
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
		drawBackground(stack, INVENTORY_SIDEBAR);




	}
}
