package andronomos.androtech.gui;

import andronomos.androtech.Const;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class TestBlockScreen extends BaseScreen {
	public TestBlockScreen(AbstractContainerMenu screenContainer, Inventory inventory, Component component) {
		super(screenContainer, inventory, component);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		drawBackground(stack, INVENTORY_PLAIN);

		this.drawSlot(stack, 0, 0, SLOT, 18);
		this.drawSlot(stack, 20, 20, SLOT_ENCHANTED_BOOK, 18);
		this.drawSlot(stack, 100, 100, SLOT_UNIT, 18);
	}
}
