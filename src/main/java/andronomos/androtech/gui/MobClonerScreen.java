package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.MobClonerContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MobClonerScreen extends BaseScreen<MobClonerContainer> {
	public MobClonerScreen(MobClonerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
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
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, INVENTORY_PLAIN);
		drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29);
	}
}
