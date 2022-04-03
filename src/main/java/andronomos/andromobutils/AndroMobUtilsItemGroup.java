package andronomos.andromobutils;

import andronomos.andromobutils.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AndroMobUtilsItemGroup extends CreativeModeTab
{
    public AndroMobUtilsItemGroup(String name) {
        super(name);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(
                ModItems.MOB_STORAGE_CELL.get()
        );
    }
}
