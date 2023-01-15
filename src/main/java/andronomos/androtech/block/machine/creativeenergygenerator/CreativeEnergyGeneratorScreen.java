package andronomos.androtech.block.machine.creativeenergygenerator;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CreativeEnergyGeneratorScreen extends MachineScreen<CreativeEnergyGeneratorMenu> {
	private PowerButton powerButton;

	public CreativeEnergyGeneratorScreen(CreativeEnergyGeneratorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		this.imageHeight = Const.SCREEN_LARGE_IMAGE_HEIGHT;
		assignEnergyInfoArea(menu.blockEntity.getEnergyStorage());
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.CREATIVE_ENERGY_GENERATOR);
		energyInfoArea.draw(stack);
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
		powerButton.update();
		renderEnergyAreaTooltips(stack, mouseX, mouseY);
	}
}
