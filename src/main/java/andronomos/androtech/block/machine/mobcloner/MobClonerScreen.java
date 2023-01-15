package andronomos.androtech.block.machine.mobcloner;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class MobClonerScreen extends MachineScreen<MobClonerMenu> {
	private PowerButton powerButton;

	public MobClonerScreen(MobClonerMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
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

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.MOB_CLONER_SCREEN);
		energyInfoArea.draw(stack);

		//menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
		//	//for(int slotCounter = 0; slotCounter < MobClonerBlockEntity.SLOTS; slotCounter++) {
		//	//	this.drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * slotCounter, 29, TextureRegistry.SLOT_UNIT, Const.SCREEN_SLOT_SIZE);
		//	//}
		//	this.drawSlot(stack, Const.SCREEN_SLOT_X_CENTER, 29, TextureRegistry.SLOT_UNIT, Const.SCREEN_SLOT_SIZE);
		//});
	}
}
