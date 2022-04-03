package andronomos.andromobtools.gui;

import andronomos.andromobtools.Const;
import andronomos.andromobtools.inventory.GoldenSpawnerContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GoldenSpawnerScreen extends BaseScreen<GoldenSpawnerContainer> {
	public GoldenSpawnerScreen(GoldenSpawnerContainer container, Inventory inventory, Component component) {
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
		drawBackground(stack, INVENTORY);
		drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29);
	}
}
