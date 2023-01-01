package andronomos.androtech.registry;

import andronomos.androtech.item.Module.BlockGPSModule;
import andronomos.androtech.item.Module.ItemAttractorModule;
import andronomos.androtech.item.Module.MendingModule;
import andronomos.androtech.item.Module.MobStasisModule;
import andronomos.androtech.item.base.AbstractDevice;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<Item> CHIP_WAFER = register("chip_wafer");
    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> ELITE_CHIP = register("elite_chip");

    public static final RegistryObject<Item> BLOCK_GPS_MODULE = ITEMS.register("block_gps_module",
            () -> new BlockGPSModule(GetBaseProperties()));

    public static final RegistryObject<Item> MOB_STASIS_MODULE = ITEMS.register("mob_stasis_module",
            () -> new MobStasisModule(GetBaseProperties().durability(AbstractDevice.DURABILITY)));

    public static final RegistryObject<Item> ITEM_ATTRACTOR_MODULE = ITEMS.register("item_attractor_module",
            () -> new ItemAttractorModule(GetBaseProperties().durability(AbstractDevice.DURABILITY)));

    public static final RegistryObject<Item> MENDING_MODULE = ITEMS.register("mending_module",
            () -> new MendingModule(GetBaseProperties().durability(AbstractDevice.DURABILITY)));

    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(GetBaseProperties()));
    }

    public static Item.Properties GetBaseProperties() {
        return new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);
    }
}
