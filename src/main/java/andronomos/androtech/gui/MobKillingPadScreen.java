package andronomos.androtech.gui;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.MobKillingPadContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MobKillingPadScreen extends BaseScreen<MobKillingPadContainer> {

    public MobKillingPadScreen(MobKillingPadContainer container, Inventory inventory, Component name) {
        super(container, inventory, name);
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
        this.drawBackground(stack, INVENTORY_PLAIN);
        this.drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 3, 29, SLOT_ENCHANTED_BOOK, 18);
        this.drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 5, 29, SLOT_ENCHANTED_BOOK, 18);
    }
}
