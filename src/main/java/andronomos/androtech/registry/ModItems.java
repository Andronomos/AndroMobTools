package andronomos.androtech.registry;

import andronomos.androtech.item.BlockGpsRecorder;
import andronomos.androtech.item.MobCloningModule;
import andronomos.androtech.item.PortableItemAttractor;
import andronomos.androtech.item.PortableItemMender;
import andronomos.androtech.item.tools.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> MOB_CLONING_MODULE = ITEMS.register("mob_cloning_module", () -> new MobCloningModule(GetBaseProperties()));
    public static final RegistryObject<Item> BLOCK_GPS_RECORDER = ITEMS.register("block_gps_recorder", () -> new BlockGpsRecorder(GetBaseProperties()));
    public static final RegistryObject<Item> PORTABLE_ITEM_ATTRACTOR = ITEMS.register("portable_item_attractor", () -> new PortableItemAttractor(GetBaseProperties()));
    public static final RegistryObject<Item> PORTABLE_ITEM_MENDER = ITEMS.register("portable_item_mender", () -> new PortableItemMender(GetBaseProperties(), true, true));
    public static final RegistryObject<Item> NANITE_ENHANCED_PICKAXE = ITEMS.register("nanite_enhanced_pickaxe", () -> new NaniteEnhancedPickAxe(Tiers.NETHERITE, 1, -2.8F, GetBaseProperties().fireResistant()));
    public static final RegistryObject<Item> NANITE_ENHANCED_AXE = ITEMS.register("nanite_enhanced_axe", () -> new NaniteEnhancedAxe(Tiers.NETHERITE, 5.0F, -3.0F, GetBaseProperties().fireResistant()));
    public static final RegistryObject<Item> NANITE_ENHANCED_SHOVEL = ITEMS.register("nanite_enhanced_shovel", () -> new NaniteEnhancedShovel(Tiers.NETHERITE, 5.0F, -3.0F, GetBaseProperties().fireResistant()));
    public static final RegistryObject<Item> NANITE_ENHANCED_SWORD = ITEMS.register("nanite_enhanced_sword", () -> new NaniteEnhancedSword(Tiers.NETHERITE, 3, -2.4F, GetBaseProperties().fireResistant()));



    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(GetBaseProperties()));
    }

    public static Item.Properties GetBaseProperties() {
        return new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);
    }
}
