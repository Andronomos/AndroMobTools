package andronomos.androtech.block.machine;

import andronomos.androtech.block.entity.ItemIncineratorBE;
import andronomos.androtech.block.machine.base.AbstractGuiMachine;
import andronomos.androtech.inventory.ItemIncineratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ItemIncinerator extends AbstractGuiMachine {
    public static final String SCREEN_ITEM_INCINERATOR = "screen.androtech.item_incinerator";

    public ItemIncinerator(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemIncineratorBE(pos, state);
    }

    @Override
    public void OpenGui(Level level, BlockPos pos, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);

        if(entity instanceof ItemIncineratorBE) {
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public TextComponent getDisplayName() {
                    return new TextComponent(SCREEN_ITEM_INCINERATOR);
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player1) {
                    return new ItemIncineratorContainer(windowId, pos, inventory);
                }
            };
            NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
        }
    }
}
