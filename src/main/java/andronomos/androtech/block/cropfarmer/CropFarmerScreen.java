package andronomos.androtech.block.cropfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CropFarmerScreen extends MachineScreen<CropFarmerContainer> {
	private PowerButton powerButton;
	private CropFarmerContainer container;

	public CropFarmerScreen(CropFarmerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.container = container;
		this.imageHeight = Const.INVENTORY_LARGE_IMAGE_HEIGHT;
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.INSTANCE.sendToServer(new SyncMachinePoweredState(container.blockEntity.getBlockPos()));
		}, container.blockEntity));
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		this.drawButtonTooltips(stack, mouseX, mouseY);
		this.drawName(stack, title.getString());
		powerButton.updateButton();
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.INVENTORY_LARGE);
	}
}
