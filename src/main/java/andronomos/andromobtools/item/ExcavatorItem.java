package andronomos.andromobtools.item;

import andronomos.andromobtools.event.HammerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.antlr.v4.runtime.misc.NotNull;

public class ExcavatorItem extends ShovelItem {

    public ExcavatorItem(Tier tier, Properties properties) {
        super(tier,1, -3.0F, properties);
    }

    @Override
    public boolean canPerformAction(@NotNull final ItemStack stack, @NotNull final ToolAction toolAction)
    {
        return ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction);
    }

    @NotNull
    @Override
    public InteractionResult useOn(final UseOnContext context)
    {
        if (!context.getLevel().isClientSide)
        {
            final ItemStack item = context.getItemInHand();
            if (item.getItem() instanceof ExcavatorItem)
            {
                final Player player = context.getPlayer();
                final Level level = context.getLevel();
                if (super.useOn(context) == InteractionResult.CONSUME && player != null)
                {
                    if (player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()))
                    {
                        for (final BlockPos pos : HammerEvents.getAffectedPos(player))
                        {
                            BlockState blockstate = FLATTENABLES.get(level.getBlockState(pos).getBlock());
                            if (level.getBlockState(pos.above()).isAir() && blockstate != null)
                            {
                                BlockState state = Blocks.DIRT_PATH.defaultBlockState();
                                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                                level.setBlock(pos, state, 11);
                            }
                        }
                        context.getItemInHand().hurtAndBreak(1, player, (p) ->
                                p.broadcastBreakEvent(context.getHand()));
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
