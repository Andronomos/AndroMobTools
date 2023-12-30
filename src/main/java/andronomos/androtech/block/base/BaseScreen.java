package andronomos.androtech.block.base;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	public BaseScreen(T menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, delta);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
	}

	//protected void drawName(GuiGraphics guiGraphics, String name) {
	//	//drawString(stack, name, (this.getXSize() - this.font.width(name)) / 2, 6.0F);
	//	this.font.dr(stack, Component.translatable(name), x, y, 4210752);
	//}

	protected void drawBackground(GuiGraphics guiGraphics, ResourceLocation texture) {
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem._setShaderTexture(0, texture);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);
	}
}
