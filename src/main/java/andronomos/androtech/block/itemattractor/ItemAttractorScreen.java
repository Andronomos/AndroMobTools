package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.MachineScreen;
import andronomos.androtech.gui.widget.button.machinebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemAttractorScreen extends MachineScreen<ItemAttractorContainer> {
	private PowerButton powerButton;
	private ItemAttractorContainer container;

	public ItemAttractorScreen(ItemAttractorContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.imageHeight = 222;
		//this.height = 114 + 6 * 18;
		this.container = container;
	}

	@Override
	protected void init() {
		super.init();

		powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
			BlockPos pos = container.blockEntity.getBlockPos();
			AndroTechPacketHandler.INSTANCE.sendToServer(new SyncMachinePoweredState(pos));
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
