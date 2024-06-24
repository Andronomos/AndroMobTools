package andronomos.androtech.fluid;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.joml.Matrix4f;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Thanks to Kaupenjoe
 * 99% copied from https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.19/blob/main/src/main/java/net/kaupenjoe/tutorialmod/screen/renderer/FluidTankRenderer.java
 * Minecraft 1.19.2 Forge Modding Tutorial | FLUID HANDLING | #26: https://www.youtube.com/watch?v=IE7ibRXYl28
 */
public class FluidTankRenderer {
	private static final NumberFormat nf = NumberFormat.getIntegerInstance();
	private static final int TEXTURE_SIZE = 16;
	private static final int MIN_FLUID_HEIGHT = 1;
	private final long capacity;
	private final int width;
	private final int height;

	public FluidTankRenderer(long capacity, int width, int height) {
		this.capacity = capacity;
		this.width = width;
		this.height = height;
	}

	public void render(PoseStack stack, int x, int y, FluidStack fluidStack) {
		RenderSystem.enableBlend();
		stack.pushPose();
		{
			stack.translate(x, y, 0);
			drawFluid(stack, width, height, fluidStack);
		}
		stack.popPose();
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.disableBlend();
	}

	private void drawFluid(PoseStack stack, final int width, final int height, FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();

		if (fluid.isSame(Fluids.EMPTY)) {
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(fluidStack);
		int fluidColor = getColorTint(fluidStack);
		long amount = fluidStack.getAmount();
		long scaledAmount = (amount * height) / capacity;

		if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
			scaledAmount = MIN_FLUID_HEIGHT;
		}

		if (scaledAmount > height) {
			scaledAmount = height;
		}

		drawTiledSprite(stack, width, height, fluidColor, scaledAmount, fluidStillSprite);
	}

	private TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
		ResourceLocation fluidStill = renderProperties.getStillTexture(fluidStack);
		Minecraft minecraft = Minecraft.getInstance();
		return minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
	}

	private int getColorTint(FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
		return renderProperties.getTintColor(fluidStack);
	}

	private void drawTiledSprite(PoseStack stack, final int tiledWidth, final int tiledHeight, int fluidColor, long scaledAmount, TextureAtlasSprite fluidStillSprite) {
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		Matrix4f matrix = stack.last().pose();
		setGLColorFromInt(fluidColor);

		final int xTileCount = tiledWidth / TEXTURE_SIZE;
		final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
		final long yTileCount = scaledAmount / TEXTURE_SIZE;
		final long yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

		final int yStart = tiledHeight;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
				long height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
				int x = (xTile * TEXTURE_SIZE);
				int y = yStart - ((yTile + 1) * TEXTURE_SIZE);
				if (width > 0 && height > 0) {
					long maskTop = TEXTURE_SIZE - height;
					int maskRight = TEXTURE_SIZE - width;

					drawTextureWithMasking(matrix, x, y, fluidStillSprite, maskTop, maskRight, 100);
				}
			}
		}
	}

	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.setShaderColor(red, green, blue, alpha);
	}

	private static void drawTextureWithMasking(Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, long maskTop, long maskRight, float zLevel) {
		float uMin = textureSprite.getU0();
		float uMax = textureSprite.getU1();
		float vMin = textureSprite.getV0();
		float vMax = textureSprite.getV1();
		uMax = uMax - (maskRight / 16F * (uMax - uMin));
		vMax = vMax - (maskTop / 16F * (vMax - vMin));

		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(matrix, xCoord, yCoord + 16, zLevel).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + 16, zLevel).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + maskTop, zLevel).uv(uMax, vMin).endVertex();
		bufferBuilder.vertex(matrix, xCoord, yCoord + maskTop, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
	}

	public List<Component> getTooltip(FluidStack fluidStack, String tooltipKey) {
		List<Component> tooltip = new ArrayList<>();
		Fluid fluidType = fluidStack.getFluid();

		if (fluidType.isSame(Fluids.EMPTY)) {
			return tooltip;
		}

		Component displayName = fluidStack.getDisplayName();
		tooltip.add(displayName);

		long amount = fluidStack.getAmount();
		long milliBuckets = (amount * 1000) / FluidType.BUCKET_VOLUME;

		MutableComponent amountString = Component.translatable(tooltipKey, nf.format(milliBuckets), nf.format(capacity));
		tooltip.add(amountString.withStyle(ChatFormatting.GRAY));

		return tooltip;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
