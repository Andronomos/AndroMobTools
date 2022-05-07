package andronomos.androtech.registry;

import andronomos.androtech.Const;
import andronomos.androtech.item.*;
import andronomos.androtech.item.activatableItem.EffectEmitter;
import andronomos.androtech.item.activatableItem.EffectNullifier;
import andronomos.androtech.item.module.BlockGpsModule;
import andronomos.androtech.item.module.ItemAttractorModule;
import andronomos.androtech.item.module.ItemRepairModule;
import andronomos.androtech.item.module.MobCloningModule;
import andronomos.androtech.item.tools.NaniteEnhancedAxe;
import andronomos.androtech.item.tools.NaniteEnhancedPickAxe;
import andronomos.androtech.item.tools.NaniteEnhancedShovel;
import andronomos.androtech.item.tools.NaniteEnhancedSword;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();
    private static final Item.Properties EMITTER_PROPERTIES = GetBaseProperties().durability(EffectEmitter.EMITTER_DURABILITY);
    private static final Item.Properties NULLIFIER_PROPERTIES = GetBaseProperties().durability(EffectNullifier.NULLIFIER_DURABILITY);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> MOB_CLONING_MODULE = ITEMS.register("mob_cloning_module", () -> new MobCloningModule(GetBaseProperties()));
    public static final RegistryObject<Item> BLOCK_GPS_MODULE = ITEMS.register("block_gps_module", () -> new BlockGpsModule(GetBaseProperties()));
    public static final RegistryObject<Item> ITEM_ATTRACTOR_MODULE = ITEMS.register("item_attractor_module", () -> new ItemAttractorModule(GetBaseProperties().durability(ItemAttractorModule.ATTRACTOR_UNIT_DURABILITY)));
    public static final RegistryObject<Item> ITEM_REPAIR_MODULE = ITEMS.register("item_repair_module", () -> new ItemRepairModule(GetBaseProperties().durability(ItemRepairModule.REPAIR_MODULE_DURABILITY)));
    public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword", () -> new FakeSword());
    public static final RegistryObject<Item> ORE_MINER = ITEMS.register("ore_miner", () -> new OreMiner(GetBaseProperties()));

    //public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStick(DEBUG_PROPERTIES.durability(1)));

    public static final RegistryObject<Item> SWIFTNESS_EMITTER = ITEMS.register("swiftness_emitter", () -> new EffectEmitter(EMITTER_PROPERTIES, MobEffects.MOVEMENT_SPEED, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> FIRE_RESISTANCE_EMITTER = ITEMS.register("fire_resistance_emitter", () -> new EffectEmitter(EMITTER_PROPERTIES, MobEffects.FIRE_RESISTANCE, Const.EffectAmplifier.I));
    public static final RegistryObject<Item> REGENERATION_EMITTER = ITEMS.register("regeneration_emitter", () -> new EffectEmitter(EMITTER_PROPERTIES, MobEffects.REGENERATION, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> NIGHT_VISION_EMITTER = ITEMS.register("night_vision_emitter", () -> new EffectEmitter(EMITTER_PROPERTIES, MobEffects.NIGHT_VISION, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> WATER_BREATHING_EMITTER = ITEMS.register("water_breathing_emitter", () -> new EffectEmitter(EMITTER_PROPERTIES, MobEffects.WATER_BREATHING, Const.EffectAmplifier.V));

    public static final RegistryObject<Item> POISON_NULLIFIER = ITEMS.register("poison_nullifier", () -> new EffectNullifier(NULLIFIER_PROPERTIES, MobEffects.POISON));
    public static final RegistryObject<Item> WITHER_NULLIFIER = ITEMS.register("wither_nullifier", () -> new EffectNullifier(NULLIFIER_PROPERTIES, MobEffects.WITHER));

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
