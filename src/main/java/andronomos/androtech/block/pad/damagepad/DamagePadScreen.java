package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DamagePadScreen extends BaseScreen<DamagePadMenu> {
	public DamagePadScreen(DamagePadMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	//@Override
	//protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	//	this.drawName(guiGraphics, title.getString());
	//}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/damage_pad.png"));
	}
}
