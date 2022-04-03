package andronomos.andromobtools;

import andronomos.andromobtools.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AndroMobToolsItemGroup extends CreativeModeTab
{
    public AndroMobToolsItemGroup(String name) {
        super(name);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(
                ModItems.MOB_STORAGE_CELL.get()
        );
    }
}
