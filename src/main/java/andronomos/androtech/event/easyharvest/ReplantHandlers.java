package andronomos.androtech.event.easyharvest;

import andronomos.androtech.util.ItemStackUtils;
import com.google.common.collect.Lists;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public class ReplantHandlers {
    private static List<BlockState> crops = Lists.newArrayList(
            //Blocks.WHEAT.defaultBlockState().with(BlockStateProperties.AGE_7, 7),
            //Blocks.NETHER_WART.defaultBlockState().with(BlockStateProperties.AGE_3, 3),
            //Blocks.CARROTS.defaultBlockState().with(BlockStateProperties.AGE_7, 7),
            //Blocks.POTATOES.defaultBlockState().with(BlockStateProperties.AGE_7, 7),
            //Blocks.BEETROOTS.defaultBlockState().with(BlockStateProperties.AGE_3, 3)
    );

    public static final IReplantHandler EASY_HANDLER = (level, pos, state, player, tileEntity) -> {
        BlockState crop = crops.stream().filter(c -> c == state).findFirst().orElse(null);

        if(crop == null) {
            return InteractionResult.PASS;
        }

        List<ItemStack> drops = Block.getDrops(state, level, pos, tileEntity, player, player.getItemInHand(InteractionHand.MAIN_HAND));
        boolean foundSeed = false;

        for (ItemStack drop : drops) {
            Item dropItem = drop.getItem();

            if(dropItem instanceof BlockItem && ((BlockItem)dropItem).getBlock() == state.getBlock()) {
                foundSeed = true;
                drop.shrink(1);
                break;
            }
        }

        if (foundSeed) {
            drops.forEach(stack -> ItemStackUtils.drop(level, pos, stack));
            level.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    };
}
