package andronomos.androtech.block.machine.redstonetransmitter;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineScreen;
import andronomos.androtech.gui.widget.button.sidebutton.PowerButton;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.SyncMachinePoweredState;
import andronomos.androtech.network.packet.SyncRedstoneTransmitterState;
import andronomos.androtech.registry.TextureRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class RedstoneTransmitterScreen extends MachineScreen<RedstoneTransmitterMenu> {
    private PowerButton powerButton;

    public RedstoneTransmitterScreen(RedstoneTransmitterMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
        this.imageHeight = Const.SCREEN_LARGE_IMAGE_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();

        powerButton = (PowerButton) this.addButton(new PowerButton((button) -> {
            BlockPos pos = menu.blockEntity.getBlockPos();
            AndroTechPacketHandler.INSTANCE.sendToServer(new SyncMachinePoweredState(pos));
            AndroTechPacketHandler.INSTANCE.sendToServer(new SyncRedstoneTransmitterState(pos));
        }, menu.blockEntity));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        this.drawButtonTooltips(stack, mouseX, mouseY);
        this.drawName(stack, title.getString());
        powerButton.update();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        this.drawBackground(poseStack, TextureRegistry.INVENTORY_PLAIN);

        menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER ).ifPresent(h -> {
            for(int slotCounter = 0; slotCounter < RedstoneTransmitterBlockEntity.SLOTS; slotCounter++) {
                this.drawSlot(poseStack, Const.SCREEN_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * slotCounter, 26, TextureRegistry.SLOT_UNIT, Const.SCREEN_SLOT_SIZE);
            }
        });
    }
}
