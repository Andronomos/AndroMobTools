package andronomos.androtech.block.itemattractor;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.PoweredStateSyncPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class ItemAttractorScreen extends BaseScreen<ItemAttractorMenu> {
	private PowerButton powerButton;

	public ItemAttractorScreen(ItemAttractorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		powerButton = (PowerButton)this.addButton(new PowerButton((button) ->
				AndroTechPacketHandler.sendToServer(new PoweredStateSyncPacket(menu.blockEntity.getBlockPos())), menu.blockEntity));
		setFluidRenderer(new FluidTankRenderer(64000, 124, 51));
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
		powerButton.update();
		renderFluidTooltips(graphics, mouseX, mouseY, 8, 19, entity.getFluidStack());
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/item_attractor.png"));
		renderFluid(guiGraphics, 8, 19, entity.getFluidStack());
	}
}
