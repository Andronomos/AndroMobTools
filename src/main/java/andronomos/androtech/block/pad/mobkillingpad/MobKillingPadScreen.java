package andronomos.androtech.block.pad.mobkillingpad;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MobKillingPadScreen extends MachineScreen<MobKillingPadMenu> {

    public MobKillingPadScreen(MobKillingPadMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
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
        this.drawBackground(stack, TextureRegistry.NO_INVENTORY_SMALL_SCREEN);
        this.drawSlot(stack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 29, TextureRegistry.SLOT_SWORD, 18);
    }

    //@Override
    //public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
    //    AndroTech.LOGGER.info("CropFarmerScreen#keyPressed | pKeyCode >> {}", pKeyCode);
    //
    //    //96 - `
    //    //258 - tab
    //
    //    if(pKeyCode == 96) {
    //        LevelUtils.displayWorkArea(menu.blockEntity.getWorkArea(), menu.blockEntity.getLevel(), Blocks.AIR);
    //    } else if(pKeyCode == 258) {
    //        LevelUtils.displayWorkArea(menu.blockEntity.getWorkArea(), menu.blockEntity.getLevel(), ModBlocks.OVERLAY.get());
    //    }
    //
    //    return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    //}
}
