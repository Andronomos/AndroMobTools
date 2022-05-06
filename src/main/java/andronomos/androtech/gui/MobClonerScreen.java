package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.block.entity.RedstoneTransmitterBE;
import andronomos.androtech.inventory.MobClonerContainer;
import andronomos.androtech.inventory.RedstoneTransmitterContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.CapabilityItemHandler;

public class MobClonerScreen extends BaseScreen<MobClonerContainer> {
	private MobClonerContainer container;

	public MobClonerScreen(MobClonerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.container = container;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		this.drawName(stack, title.getString());
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(poseStack, INVENTORY_PLAIN);
		drawSlot(poseStack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29);

		container.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			for(int slotCounter = 0; slotCounter < MobClonerBE.CLONER_SLOTS; slotCounter++) {
				this.drawSlot(poseStack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * slotCounter, 26, SLOT_UNIT, Const.SCREEN_SLOT_SIZE);
			}
		});
	}
}
