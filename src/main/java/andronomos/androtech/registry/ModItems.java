package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.item.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static andronomos.androtech.item.EffectEmitterItem.EMITTER_DURABILITY;
import static andronomos.androtech.item.EffectNullifierItem.NULLIFIER_DURABILITY;
import static andronomos.androtech.item.PortableLootAttractorItem.PORTABLE_LOOT_ATTRACTOR_DURABILITY;

public class ModItems {
    public static final Item.Properties BASE_PROPERTIES = new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();
    private static final Item.Properties EMITTER_PROPERTIES = new Item.Properties().tab(AndroTech.ANDROTECH_TAB).durability(EMITTER_DURABILITY);
    private static final Item.Properties NULLIFIER_PROPERTIES = new Item.Properties().tab(AndroTech.ANDROTECH_TAB).durability(NULLIFIER_DURABILITY);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    /** Debugging items **/
    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStickItem(DEBUG_PROPERTIES.durability(10)));

    /** Crafting components **/
    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> WITHERED_BONE = ITEMS.register("withered_bone", () -> new Item(BASE_PROPERTIES));

    /** Equipment **/
    public static final RegistryObject<Item> MOB_STORAGE_CELL = ITEMS.register("mob_storage_cell", () -> new MobStorageCellItem(BASE_PROPERTIES));
    public static final RegistryObject<Item> PORTABLE_LOOT_ATTRACTOR = ITEMS.register("portable_loot_attractor",
            () -> new PortableLootAttractorItem(BASE_PROPERTIES.durability(PORTABLE_LOOT_ATTRACTOR_DURABILITY)));

    public static final RegistryObject<Item> SPEED_EMITTER = ITEMS.register("speed_emitter",
            () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.MOVEMENT_SPEED, Const.EffectAmplifier.V));

    public static final RegistryObject<Item> FIRE_RESISTANCE_EMITTER = ITEMS.register("fire_resistance_emitter",
            () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.FIRE_RESISTANCE, Const.EffectAmplifier.I));

    public static final RegistryObject<Item> REGENERATION_EMITTER = ITEMS.register("regeneration_emitter",
            () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.REGENERATION, Const.EffectAmplifier.V));

    public static final RegistryObject<Item> POISON_NULLIFIER = ITEMS.register("poison_nullifier",
            () -> new EffectNullifierItem(NULLIFIER_PROPERTIES, MobEffects.POISON));

    public static final RegistryObject<Item> WITHER_NULLIFIER = ITEMS.register("wither_nullifier",
            () -> new EffectNullifierItem(NULLIFIER_PROPERTIES, MobEffects.WITHER));

    /** Misc **/
    public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword", () -> new FakeSword());
    public static final RegistryObject<Item> GPS_CARD = ITEMS.register("gps_card", () -> new GPSCardItem(BASE_PROPERTIES));


    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(BASE_PROPERTIES));
    }
}
