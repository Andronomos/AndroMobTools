package andronomos.andromobtools.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class HammerItem extends PickaxeItem {

    public HammerItem(final Tier tier, Properties properties) {
        super(tier, 1, -2.8F, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.PICKAXE_DIG;
    }
}
