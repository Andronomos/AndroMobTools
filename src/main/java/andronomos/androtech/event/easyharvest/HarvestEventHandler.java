package andronomos.androtech.event.easyharvest;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HarvestEventHandler {
    @SubscribeEvent
    public static void onCropHarvested(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();

        if(!(level instanceof ServerLevel))
            return;

        InteractionHand hand = event.getHand();

        if (hand != InteractionHand.MAIN_HAND)
            return;

        Player player = event.getEntity();
        BlockState state = level.getBlockState(event.getPos());
        IReplantHandler handler = ReplantHandlers.EASY_HANDLER;
        InteractionResult result = handler.handlePlant((ServerLevel) event.getLevel(), event.getPos(), state, event.getEntity(), level.getBlockEntity(event.getPos()));

        if(result == InteractionResult.SUCCESS) {
            player.swing(hand);
        }
    }
}
