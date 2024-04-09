package andronomos.androtech.block;

import andronomos.androtech.inventory.client.SideButton;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	private final List<SideButton> sideButtons = new ArrayList<>();
	private int sideButtonY;
	private static final int BUTTON_LEFT = 109;

	public BaseScreen(T menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		sideButtonY = this.topPos;
		sideButtons.clear();
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, delta);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
	}

	public SideButton addButton(SideButton button) {
		button.setX(this.width / 2 - BUTTON_LEFT);
		button.setY(sideButtonY);
		sideButtonY += button.getHeight() + 2;
		sideButtons.add(button);
		return this.addRenderableWidget(button);
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
