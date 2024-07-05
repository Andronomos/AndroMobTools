package andronomos.androtech.block.fluidtank;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.fluid.FluidTankRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FluidTankScreen extends BaseScreen<FluidTankMenu> {
	private final int X_OFFSET = 152; //todo: update when GUI is completed
	private final int Y_OFFSET = 18; //todo: update when GUI is completed
	private final FluidTankBlockEntity entity;

	public FluidTankScreen(FluidTankMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		entity = (FluidTankBlockEntity) menu.blockEntity;
	}

	@Override
	protected void init() {
		super.init();
		setFluidRenderer(new FluidTankRenderer(AndroTechConfig.FLUID_TANK_CAPACITY.get() * 1000, 16, 52));
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		renderFluidTooltips(graphics, mouseX, mouseY, X_OFFSET, Y_OFFSET, entity.getFluidStack(), FluidTankBlock.AMOUNT_TOOLTIP);
		super.renderLabels(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/fluid_tank.png"));
		renderFluid(guiGraphics, X_OFFSET, Y_OFFSET, entity.getFluidStack());
	}
}
