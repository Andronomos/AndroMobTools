package andronomos.androtech.block.itemincinerator;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.BaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemIncineratorScreen extends BaseScreen<ItemIncineratorMenu> {

    public ItemIncineratorScreen(ItemIncineratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        drawBackground(guiGraphics, new ResourceLocation(AndroTech.MODID, "textures/gui/item_incinerator.png"));
    }
}
