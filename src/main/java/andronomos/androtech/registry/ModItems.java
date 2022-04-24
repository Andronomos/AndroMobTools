package andronomos.androtech.registry;

import andronomos.androtech.Const;
import andronomos.androtech.item.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static andronomos.androtech.item.EffectEmitterItem.EMITTER_DURABILITY;
import static andronomos.androtech.item.EffectNullifierItem.NULLIFIER_DURABILITY;
import static andronomos.androtech.item.AttractorUnitItem.ATTRACTOR_UNIT_DURABILITY;

public class ModItems {
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();
    private static final Item.Properties EMITTER_PROPERTIES = GetBaseProperties().durability(EMITTER_DURABILITY);
    private static final Item.Properties NULLIFIER_PROPERTIES = GetBaseProperties().durability(NULLIFIER_DURABILITY);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    /** Debugging items **/
    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStickItem(DEBUG_PROPERTIES.durability(1)));

    /** Crafting components **/
    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> WITHERED_BONE = ITEMS.register("withered_bone", () -> new Item(GetBaseProperties()));

    /** Equipment **/
    public static final RegistryObject<Item> ATTRACTOR_UNIT = ITEMS.register("attractor_unit",
            () -> new AttractorUnitItem(GetBaseProperties().durability(ATTRACTOR_UNIT_DURABILITY)));

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
    public static final RegistryObject<Item> DNA_UNIT = ITEMS.register("dna_unit", () -> new DnaUnitItem(GetBaseProperties()));
    public static final RegistryObject<Item> GPS_UNIT = ITEMS.register("gps_unit", () -> new GpsUnitItem(GetBaseProperties()));
    public static final RegistryObject<Item> NANITE_UNIT = ITEMS.register("nanite_unit", () -> new NaniteUnitItem(GetBaseProperties().durability(ATTRACTOR_UNIT_DURABILITY)));


    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(GetBaseProperties()));
    }

    public static Item.Properties GetBaseProperties() {
        return new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);
    }
}
