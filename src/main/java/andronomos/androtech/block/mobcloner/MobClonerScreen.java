package andronomos.androtech.block.mobcloner;

import andronomos.androtech.Const;
import andronomos.androtech.block.MachineScreen;
import andronomos.androtech.gui.widget.button.machinebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.CapabilityItemHandler;

public class MobClonerScreen extends MachineScreen<MobClonerContainer> {
	private PowerButton powerButton;
	private MobClonerContainer container;

	public MobClonerScreen(MobClonerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
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
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(poseStack, TextureRegistry.INVENTORY_PLAIN);

		container.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			for(int slotCounter = 0; slotCounter < MobClonerBE.CLONER_SLOTS; slotCounter++) {
				this.drawSlot(poseStack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * slotCounter, 29, TextureRegistry.SLOT_UNIT, Const.SCREEN_SLOT_SIZE);
			}
		});
	}
}
