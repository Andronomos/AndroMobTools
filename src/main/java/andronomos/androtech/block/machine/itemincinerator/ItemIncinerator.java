package andronomos.androtech.block.machine.itemincinerator;

import andronomos.androtech.block.machine.GuiMachine;
import andronomos.androtech.block.machine.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ItemIncinerator extends GuiMachine {
    public static final String DISPLAY_NAME = "screen.androtech.item_incinerator";
    public static final String TOOLTIP = "block.androtech.item_incinerator.tooltip";

    public ItemIncinerator(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture, boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
        super(properties, useDefaultSideTexture, useDefaultBottomTexture, useDefaultTopTexture, useDefaultFrontTexture, hasMultipleStates);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemIncineratorBlockEntity(pos, state);
    }

    @Override
    public void OpenScreen(Level level, BlockPos pos, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);

        if(entity instanceof ItemIncineratorBlockEntity itemIncineratorBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) player, itemIncineratorBlockEntity, entity.getBlockPos());
        } else {
            throw new IllegalStateException("Missing container provider");
        }
    }
}
