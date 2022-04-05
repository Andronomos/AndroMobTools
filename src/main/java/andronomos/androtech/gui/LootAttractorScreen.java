package andronomos.androtech.gui;

import andronomos.androtech.inventory.LootAttractorContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LootAttractorScreen extends BaseScreen<LootAttractorContainer> {
	public LootAttractorScreen(LootAttractorContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.imageHeight = 114 + 6 * 18;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int i, int j) {
		this.drawName(stack, title.getString());
		//this.font.draw(stack, this.inventory.getDisplayName().getString(), 8.0F, (float) (this.imageHeight - 95), 0x404040);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, INVENTORY_LARGE);
	}
}
