package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.LootIncineratorContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LootIncineratorScreen extends BaseScreen<LootIncineratorContainer> {

    public LootIncineratorScreen(LootIncineratorContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
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
        drawBackground(stack, INVENTORY);
        drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29);
    }
}
