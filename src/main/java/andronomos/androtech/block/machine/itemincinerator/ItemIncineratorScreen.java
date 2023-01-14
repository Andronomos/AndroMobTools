package andronomos.androtech.block.machine.itemincinerator;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemIncineratorScreen extends MachineScreen<ItemIncineratorMenu> {

    public ItemIncineratorScreen(ItemIncineratorMenu container, Inventory inventory, Component component) {
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
        drawBackground(stack, TextureRegistry.NO_INVENTORY_SMALL_SCREEN);
        drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29, TextureRegistry.SLOT, Const.SCREEN_SLOT_SIZE);
    }
}
