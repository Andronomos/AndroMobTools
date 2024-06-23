package andronomos.androtech.block.ExperienceAttractor;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.fluid.FluidTankRenderer;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.inventory.client.RenderOutlineButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.ExperienceAttractorOverlaySyncPacket;
import andronomos.androtech.network.packet.MobKillerOverlaySyncPacket;
import andronomos.androtech.network.packet.PoweredStateSyncPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ExperienceAttractorScreen extends BaseScreen<ExperienceAttractorMenu> {
	private PowerButton powerButton;
	private RenderOutlineButton overlayButton;
	private FluidTankRenderer fluidRenderer;
	private final ExperienceAttractorBlockEntity entity;

	public ExperienceAttractorScreen(ExperienceAttractorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		entity = (ExperienceAttractorBlockEntity)menu.blockEntity;
	}

	@Override
	protected void init() {
		super.init();
		powerButton = (PowerButton)this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.sendToServer(new PoweredStateSyncPacket(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));
		overlayButton = (RenderOutlineButton)this.addButton(new RenderOutlineButton((button) -> {
			AndroTechPacketHandler.sendToServer(new ExperienceAttractorOverlaySyncPacket(entity.getBlockPos()));
			entity.showRenderBox = !entity.showRenderBox;
		}));
		fluidRenderer = new FluidTankRenderer(64000, 124, 51);
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderLabels(guiGraphics, mouseX, mouseY);
		powerButton.update();
		overlayButton.update(entity.showRenderBox);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		renderFluidAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/experience_attractor.png"));
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		ExperienceAttractorBlockEntity entity = (ExperienceAttractorBlockEntity)menu.blockEntity;
		FluidStack fluidStack = entity.getFluidStack();
		fluidRenderer.render(guiGraphics.pose(), x + 8, y + 19, fluidStack);
	}

	private void renderFluidAreaTooltips(GuiGraphics graphics, int pMouseX, int pMouseY, int x, int y) {
		if(isMouseAboveArea(pMouseX, pMouseY, x, y, 8, 19)) {
			graphics.renderTooltip(this.font, fluidRenderer.getTooltip(((ExperienceAttractorBlockEntity)menu.blockEntity).getFluidStack(), TooltipFlag.Default.NORMAL), Optional.empty(), pMouseX - x, pMouseY - y);
		}
	}

	private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY) {
		return isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, fluidRenderer.getWidth(), fluidRenderer.getHeight());
	}

	public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
		return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
	}
}
