package andronomos.androtech.inventory.client;

import andronomos.androtech.AndroTech;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SideButton extends ExtendedButton {
	private static final ResourceLocation TEXTURES = new ResourceLocation(AndroTech.MODID, "textures/gui/icons.png");
	private static final int WIDTH = 18;
	private static final int HEIGHT = 18;
	private TextureEnum currentTexture;

	public SideButton(TextureEnum currentTexture, OnPress handler) {
		super(-1, -1, WIDTH, HEIGHT, Component.empty(), handler);
		this.currentTexture = currentTexture;
	}

	@Override
	public void renderWidget(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
		super.renderWidget(gg, mouseX, mouseY, partialTick);
		if (visible) {
			boolean hover = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + width && mouseY < getY() + height;

			if (hover) {
				gg.setColor(0.75f, 1f, 0.75f, 1f);
			} else {
				gg.setColor(0.75f, 0.75f, 0.75f, 0.5f);
			}

			gg.blit(TEXTURES, getX(), getY(), currentTexture.getX(), currentTexture.getY(), currentTexture.getWidth(), currentTexture.getHeight());
			gg.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	public void setCurrentTexture(TextureEnum id) {
		this.currentTexture = id;
	}

	public int getHeight() {
		return height;
	}
}
