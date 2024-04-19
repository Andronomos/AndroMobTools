package andronomos.androtech.block.itemincinerator;

import andronomos.androtech.base.BaseMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ItemIncineratorBlock extends BaseMachineBlock {
    public static final int SLOTS = 1;
    public static final String DISPLAY_NAME = "screen.androtech.item_incinerator";
    public static final String TOOLTIP = "block.androtech.item_incinerator.tooltip";

    public ItemIncineratorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
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
