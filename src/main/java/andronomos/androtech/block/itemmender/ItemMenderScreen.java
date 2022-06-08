package andronomos.androtech.block.itemmender;

import andronomos.androtech.block.MachineScreen;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemMenderScreen extends MachineScreen<ItemMenderContainer> {
	public ItemMenderScreen(ItemMenderContainer container, Inventory inventory, Component component) {
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
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		this.drawName(stack, title.getString());
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, TextureRegistry.INVENTORY_LARGE);
	}
}
