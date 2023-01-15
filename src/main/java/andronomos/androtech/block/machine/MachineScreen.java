package andronomos.androtech.block.machine;

import andronomos.androtech.block.machine.cropfarmer.renderer.EnergyInfoArea;
import andronomos.androtech.gui.widget.button.sidebutton.SideButton;
import andronomos.androtech.util.MouseUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MachineScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	private final List<SideButton> sideButtons = new ArrayList<>();
	private int sideButtonY;
	private static final int BUTTON_LEFT = 109;
	public EnergyInfoArea energyInfoArea;

	public MachineScreen(T menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		sideButtonY = this.topPos;
		sideButtons.clear();
	}

	protected void drawBackground(PoseStack stack, ResourceLocation gui) {
		RenderSystem.setShaderTexture(0, gui);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(stack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
	}

	protected void drawName(PoseStack stack, String name) {
		drawString(stack, name, (this.getXSize() - this.font.width(name)) / 2, 6.0F);
	}

	protected void drawString(PoseStack stack, String name, float x, float y) {
		this.font.draw(stack, Component.translatable(name), x, y, 4210752);
	}

	protected void drawSlot(PoseStack stack, int x, int y, ResourceLocation texture, int size) {
		RenderSystem.setShaderTexture(0, texture);
		blit(stack, getGuiLeft() + x, getGuiTop() + y, 0, 0, size, size, size, size);
	}

	public SideButton addButton(SideButton button) {
		button.x = this.width / 2 - BUTTON_LEFT;
		button.y = sideButtonY;
		sideButtonY += button.getHeight() + 2;
		sideButtons.add(button);
		return this.addRenderableWidget(button);
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

	public void assignEnergyInfoArea(IEnergyStorage energyStorage) {
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		energyInfoArea = new EnergyInfoArea(relX + 156, relY + 13, energyStorage);
	}

	public void renderEnergyAreaTooltips(PoseStack stack, int mouseX, int mouseY) {
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
	//protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
	//	AndroTech.LOGGER.info("MachineScreen#slotClicked | pSlotId >> {}", pSlotId);
	//	super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
	//}
}
