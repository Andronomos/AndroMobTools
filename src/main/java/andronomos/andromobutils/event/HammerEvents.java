package andronomos.andromobutils.event;

import andronomos.andromobutils.item.ExcavatorItem;
import andronomos.andromobutils.item.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class HammerEvents {

    private static final ResourceLocation CAN_HAMMER = new ResourceLocation("hammers", "can_hammer");
    private static final ResourceLocation CAN_SHOVEL = new ResourceLocation("hammers", "can_excavate");


    @SubscribeEvent
    public static void onBlockBreak(@NotNull final BlockEvent.BreakEvent event) {
        if (event.getState().canOcclude()) {
            final ItemStack mainHand = event.getPlayer().getMainHandItem();
            final Level level = event.getPlayer().getCommandSenderWorld();
            final double hardness = event.getState().getDestroySpeed(level, event.getPos());
            for (BlockPos pos : getAffectedPos(event.getPlayer())) {
                final BlockState state = level.getBlockState(pos);
                if (hardness * 2 >= state.getDestroySpeed(level, pos) && isBestTool(state, level, pos, mainHand, event.getPlayer()))
                {
                    state.getBlock().playerDestroy(level, event.getPlayer(), pos, state, level.getBlockEntity(pos), mainHand);
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    private static boolean isBestTool(final BlockState target, final LevelAccessor level, final BlockPos pos, final ItemStack stack, final Player player)
    {
        Item item = stack.getItem();

        if ((item instanceof HammerItem && BlockTags.getAllTags().getTag(CAN_HAMMER).contains(target.getBlock()))
                || (item instanceof ExcavatorItem && BlockTags.getAllTags().getTag(CAN_SHOVEL).contains(target.getBlock())))
        {
            return true;
        }

        return stack.isCorrectToolForDrops(target);
    }

    public static BlockHitResult rayTrace(final Level level, final Player player, final ClipContext.Fluid mode)
    {
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        Vec3 vec3 = player.getEyePosition(1.0F);
        float cosYaw = Mth.cos(-yaw * 0.017453292F - 3.1415927F);
        float sinYaw = Mth.sin(-yaw * 0.017453292F - 3.1415927F);
        float cosPitch = -Mth.cos(-pitch * 0.017453292F);
        float sinPitch = Mth.sin(-pitch * 0.017453292F);
        float product = sinYaw * cosPitch;
        float product2 = cosYaw * cosPitch;
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 vec32 = vec3.add((double) product * reachDistance, (double) sinPitch * reachDistance, (double) product2 * reachDistance);
        return level.clip(new ClipContext(vec3, vec32, ClipContext.Block.OUTLINE, mode, player));
    }

    /**
     * Get all affected pos for a player with a tool.
     *
     * @param player the player.
     * @return the list of affected positions.
     */
    public static List<BlockPos> getAffectedPos(@NotNull final Player player)
    {
        final List<BlockPos> list = new ArrayList<>();
        final HitResult rayTrace = rayTrace(player.level, player, ClipContext.Fluid.NONE);

        if (rayTrace instanceof BlockHitResult)
        {
            final BlockHitResult rayTraceResult = (BlockHitResult) rayTrace;
            final BlockPos center = rayTraceResult.getBlockPos();
            switch (rayTraceResult.getDirection())
            {
                case DOWN:
                case UP:
                    list.add(center.west());
                    list.add(center.east());
                    list.add(center.north());
                    list.add(center.south());

                    list.add(center.west().north());
                    list.add(center.west().south());
                    list.add(center.east().north());
                    list.add(center.east().south());
                    break;
                case NORTH:
                case SOUTH:
                    list.add(center.above());
                    list.add(center.below());
                    list.add(center.west());
                    list.add(center.east());

                    list.add(center.west().above());
                    list.add(center.west().below());
                    list.add(center.east().above());
                    list.add(center.east().below());
                    break;
                case EAST:
                case WEST:
                    list.add(center.above());
                    list.add(center.below());
                    list.add(center.north());
                    list.add(center.south());

                    list.add(center.north().above());
                    list.add(center.north().below());
                    list.add(center.south().above());
                    list.add(center.south().below());
                    break;
            }
        }

        return list;
    }
}
