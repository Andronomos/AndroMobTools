package andronomos.androtech;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class AndroTechItemGroup extends CreativeModeTab
{
    public AndroTechItemGroup(String name) {
        super(name);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(
                //ModBlocks.CROP_FARMER.get()
                Blocks.COMMAND_BLOCK
        );
    }
}
