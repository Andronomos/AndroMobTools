package andronomos.androtech.block.entityvacuum;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import andronomos.androtech.fluid.FluidTankRenderer;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.inventory.client.RenderOutlineButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.EntityVacuumOverlaySyncPacket;
import andronomos.androtech.network.packet.PoweredStateSyncPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EntityVacuumScreen extends BaseScreen<EntityVacuumMenu> {
	private final int X_OFFSET = 152;
	private final int Y_OFFSET = 18;
	private final EntityVacuumBlockEntity entity;
	private PowerButton powerButton;
	private RenderOutlineButton overlayButton;

	public EntityVacuumScreen(EntityVacuumMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		entity = (EntityVacuumBlockEntity)menu.blockEntity;
	}

	@Override
	protected void init() {
		super.init();
		powerButton = (PowerButton)this.addButton(new PowerButton((button) ->
				AndroTechPacketHandler.sendToServer(new PoweredStateSyncPacket(menu.blockEntity.getBlockPos())), menu.blockEntity));
		overlayButton = (RenderOutlineButton)this.addButton(new RenderOutlineButton((button) -> {
			AndroTechPacketHandler.sendToServer(new EntityVacuumOverlaySyncPacket(entity.getBlockPos()));
			entity.showRenderBox = !entity.showRenderBox;
		}));
		setFluidRenderer(new FluidTankRenderer(64000, 16, 52));
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
		powerButton.update();
		overlayButton.update(entity.showRenderBox);
		renderFluidTooltips(graphics, mouseX, mouseY, X_OFFSET, Y_OFFSET, entity.getFluidStack(), EntityVacuumBlock.AMOUNT_TOOLTIP);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/entity_vacuum.png"));
		renderFluid(guiGraphics, X_OFFSET, Y_OFFSET, entity.getFluidStack());
	}
}
