package andronomos.androtech.gui.widget.button;

import andronomos.androtech.registry.TextureRegistry;
import andronomos.androtech.gui.TextureEnum;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import java.util.ArrayList;
import java.util.List;

public class SideButton extends ExtendedButton {
	private static final int WIDTH = 18;
	private static final int HEIGHT = 18;
	private TextureEnum currentTexture;
	private List<Component> tooltip;

	public SideButton(TextureEnum currentTexture, Button.OnPress handler) {
		super(-1, -1, WIDTH, HEIGHT, TextComponent.EMPTY, handler);
		this.currentTexture = currentTexture;
	}

	public void setCurrentTexture(TextureEnum id) {
		this.currentTexture = id;
	}

	public List<Component> getTooltip() {
		return tooltip;
	}

	public void setTooltip(String toolTipText) {
		tooltip = new ArrayList<>();
		addTooltip(toolTipText);
	}

	public void addTooltip(String toolTipText) {
		tooltip.add(new TranslatableComponent(toolTipText));
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		super.renderButton(poseStack, mouseX, mouseY, partialTicks);
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TextureRegistry.ICONS);

		if (currentTexture != null) {
			this.blit(poseStack,
					this.x, this.y,
					currentTexture.getX(), currentTexture.getY(),
					currentTexture.getWidth(), currentTexture.getHeight());
		}

		this.renderBg(poseStack, minecraft, mouseX, mouseY);
	}
}
