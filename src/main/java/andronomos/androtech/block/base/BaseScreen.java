package andronomos.androtech.block.base;

import andronomos.androtech.fluid.FluidTankRenderer;
import andronomos.androtech.inventory.client.SideButton;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	private static final int BUTTON_LEFT = 109;
	private final List<SideButton> sideButtons = new ArrayList<>();
	private int sideButtonY;
	private FluidTankRenderer fluidRenderer;

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
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
	}

	public SideButton addButton(SideButton button) {
		button.setX(this.width / 2 - BUTTON_LEFT);
		button.setY(sideButtonY);
		sideButtonY += button.getHeight() + 2;
		sideButtons.add(button);
		return this.addRenderableWidget(button);
	}

	public void setFluidRenderer(FluidTankRenderer renderer) {
		fluidRenderer = renderer;
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

	protected void renderFluid(@NotNull GuiGraphics graphics, int offsetX, int offsetY, FluidStack fluidStack) {
		if(fluidRenderer != null) {
			int x = (this.width - this.imageWidth) / 2;
			int y = (this.height - this.imageHeight) / 2;
			fluidRenderer.render(graphics.pose(), x + offsetX, y + offsetY, fluidStack);
		}
	}

	protected void renderFluidTooltips(GuiGraphics graphics, int mouseX, int mouseY, int offsetX, int offsetY, FluidStack fluidStack) {
		if(fluidRenderer == null) {
			return;
		}

		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY)) {
			graphics.renderTooltip(this.font, fluidRenderer.getTooltip(fluidStack, TooltipFlag.Default.NORMAL), Optional.empty(), mouseX - x, mouseY - y);
		}
	}

	private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY) {
		return isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, fluidRenderer.getWidth(), fluidRenderer.getHeight());
	}

	public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
		return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
	}
}
