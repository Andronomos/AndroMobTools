package andronomos.androtech.block.machine;

import andronomos.androtech.registry.TextureRegistry;
import andronomos.androtech.gui.widget.button.SideButton;
import andronomos.androtech.util.ChatUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	private final List<SideButton> sideButtons = new ArrayList<>();
	private int sideButtonY;
	private static final int BUTTON_LEFT = 109;

	public MachineScreen(T screenContainer, Inventory inventory, Component component) {
		super(screenContainer, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		sideButtonY = this.topPos;
		sideButtons.clear();
	}

	public SideButton addButton(SideButton button) {
		button.x = this.width / 2 - BUTTON_LEFT;
		button.y = sideButtonY;
		sideButtonY += button.getHeight() + 2;
		sideButtons.add(button);
		return this.addRenderableWidget(button);
	}

	protected void drawBackground(PoseStack stack, ResourceLocation gui) {
		RenderSystem.setShaderTexture(0, gui);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(stack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
	}

	protected void drawName(PoseStack stack, String name) {
		name = ChatUtil.createTranslation(name);
		drawString(stack, name, (this.getXSize() - this.font.width(name)) / 2, 6.0F);
	}

	protected void drawString(PoseStack stack, String name, float x, float y) {
		this.font.draw(stack, ChatUtil.createTranslation(name), x, y, 4210752);
	}

	protected void drawSlot(PoseStack stack, int x, int y, ResourceLocation texture, int size) {
		RenderSystem.setShaderTexture(0, texture);
		blit(stack, getGuiLeft() + x, getGuiTop() + y, 0, 0, size, size, size, size);
	}

	protected void drawSlot(PoseStack stack, int x, int y) {
		drawSlot(stack, x, y, TextureRegistry.SLOT, 18);
	}

	public void drawButtonTooltips(PoseStack stack, int mouseX, int mouseY) {
		for (GuiEventListener btn : this.children()) {
			if (btn instanceof SideButton sideButton && btn.isMouseOver(mouseX, mouseY)) {
				sideButton.renderToolTip(stack, mouseX, mouseY);
				List<Component> localTooltip = sideButton.getTooltip();
				if (localTooltip != null) {
					this.renderComponentTooltip(stack, localTooltip, mouseX - leftPos, mouseY - topPos);
				}
			}
		}
	}
}
