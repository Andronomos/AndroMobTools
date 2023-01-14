package andronomos.androtech.block.machine.itemmender;

import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemMenderScreen extends MachineScreen<ItemMenderMenu> {
	public ItemMenderScreen(ItemMenderMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
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
		drawBackground(stack, TextureRegistry.DOUBLE_INVENTORY_SCREEN);
	}
}
