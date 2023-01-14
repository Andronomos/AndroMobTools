package andronomos.androtech.block.machine.itemattractor;

import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemAttractorScreen extends MachineScreen<ItemAttractorMenu> {
	private PowerButton powerButton;

	public ItemAttractorScreen(ItemAttractorMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
		this.imageHeight = 222;
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			AndroTechPacketHandler.INSTANCE.sendToServer(new SyncMachinePoweredState(menu.blockEntity.getBlockPos()));
		}, menu.blockEntity));
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
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.DOUBLE_INVENTORY_SCREEN);
	}
}
