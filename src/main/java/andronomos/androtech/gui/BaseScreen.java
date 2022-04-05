package andronomos.androtech.gui;

import andronomos.androtech.util.ChatUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	public static final ResourceLocation INVENTORY = new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "textures/gui/inventory.png");
	public static final ResourceLocation INVENTORY_PLAIN = new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "textures/gui/inventory_plain.png");
	public static final ResourceLocation INVENTORY_LARGE = new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "textures/gui/inventory_large.png");
	public static final ResourceLocation SLOT = new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "textures/gui/slot.png");
	public static final ResourceLocation SLOT_ENCHANTED_BOOK = new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "textures/gui/slot_enchanted_book.png");

	public BaseScreen(T screenContainer, Inventory inventory, Component component) {
		super(screenContainer, inventory, component);
	}

	protected void drawBackground(PoseStack stack, ResourceLocation gui) {
		RenderSystem.setShaderTexture(0, gui);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(stack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
	}

	/**
	 * Translate the block name; and draw it in the top center
	 *
	 * @param name
	 */
	protected void drawName(PoseStack stack, String name) {
		name = ChatUtil.createTranslation(name);
		drawString(stack, name, (this.getXSize() - this.font.width(name)) / 2, 6.0F);
	}

	protected void drawString(PoseStack stack, String name, float x, float y) {
		this.font.draw(stack, ChatUtil.createTranslation(name), x, y, 4210752);
	}

	protected void drawSlot(PoseStack stack, int x, int y, ResourceLocation texture, int size) {
		//this.minecraft.getTextureManager().bindForSetup(texture);
		RenderSystem.setShaderTexture(0, texture);
		blit(stack, getGuiLeft() + x, getGuiTop() + y, 0, 0, size, size, size, size);
	}

	protected void drawSlot(PoseStack stack, int x, int y) {
		drawSlot(stack, x, y, SLOT, 18);
	}
}
