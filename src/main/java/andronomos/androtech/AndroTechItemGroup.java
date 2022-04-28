package andronomos.androtech;

import andronomos.androtech.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AndroTechItemGroup extends CreativeModeTab
{
    public AndroTechItemGroup(String name) {
        super(name);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(
                ModItems.MOB_TRANSPORTER_MODULE.get()
        );
    }
}
