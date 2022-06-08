package andronomos.androtech.block.machine.pad.mobkillingpad;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MobKillingPadScreen extends MachineScreen<MobKillingPadContainer> {

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
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        this.drawBackground(stack, TextureRegistry.INVENTORY_PLAIN);
        this.drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29, TextureRegistry.SLOT_SWORD, 18);
    }
}
