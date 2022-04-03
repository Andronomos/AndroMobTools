package andronomos.andromobutils.registry;

import andronomos.andromobutils.AndroMobUtils;
import andronomos.andromobutils.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties BASE_PROPERTIES = new Item.Properties().tab(AndroMobUtils.ANDROMOBUTILS_TAB);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AndroMobUtils.MOD_ID);

    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> MOB_STORAGE_CELL = ITEMS.register("mob_storage_cell", () -> new MobStorageCellItem(BASE_PROPERTIES));
    public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword", () -> new FakeSword());
    public static final RegistryObject<Item> PORTABLE_LOOT_ATTRACTOR = ITEMS.register("portable_loot_attractor", () -> new PortableLootAttractorItem(BASE_PROPERTIES));

    public static final RegistryObject<HammerItem> IRON_HAMMER = registerHammer("iron", Tiers.IRON);
    public static final RegistryObject<HammerItem> GOLD_HAMMER = registerHammer("gold", Tiers.GOLD);
    public static final RegistryObject<HammerItem> DIAMOND_HAMMER = registerHammer("diamond", Tiers.DIAMOND);
    public static final RegistryObject<HammerItem> NETHERITE_HAMMER = registerHammer("netherite", Tiers.NETHERITE);

    public static final RegistryObject<ExcavatorItem> IRON_EXCAVATOR = registerExcavator("iron", Tiers.IRON);
    public static final RegistryObject<ExcavatorItem> GOLD_EXCAVATOR = registerExcavator("gold", Tiers.GOLD);
    public static final RegistryObject<ExcavatorItem> DIAMOND_EXCAVATOR = registerExcavator("diamond", Tiers.DIAMOND);
    public static final RegistryObject<ExcavatorItem> NETHERITE_EXCAVATOR = registerExcavator("netherite", Tiers.NETHERITE);

    //private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block) {
    //    return ITEMS.register(name, () -> new BlockItem(block.get(), BASE_PROPERTIES));
    //}

    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(BASE_PROPERTIES));
    }

    private static RegistryObject<HammerItem> registerHammer(String name, Tier tier) {
        return ITEMS.register(name + "_hammer", () -> new HammerItem(tier, BASE_PROPERTIES));
    }

    private static RegistryObject<ExcavatorItem> registerExcavator(String name, Tier tier) {
        return ITEMS.register(name + "_excavator", () -> new ExcavatorItem(tier, BASE_PROPERTIES));
    }
}
