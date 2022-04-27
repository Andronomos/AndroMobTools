package andronomos.androtech.registry;

import andronomos.androtech.Const;
import andronomos.androtech.item.*;
import andronomos.androtech.item.activatableItem.EffectEmitterItem;
import andronomos.androtech.item.activatableItem.EffectNullifierItem;
import andronomos.androtech.item.activatableItem.ItemAttractorModuleItem;
import andronomos.androtech.item.activatableItem.NaniteRepairModuleItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static andronomos.androtech.item.activatableItem.EffectEmitterItem.EMITTER_DURABILITY;
import static andronomos.androtech.item.activatableItem.EffectNullifierItem.NULLIFIER_DURABILITY;
import static andronomos.androtech.item.activatableItem.ItemAttractorModuleItem.ATTRACTOR_UNIT_DURABILITY;

public class ModItems {
    public static final Item.Properties DEBUG_PROPERTIES = new Item.Properties();
    private static final Item.Properties EMITTER_PROPERTIES = GetBaseProperties().durability(EMITTER_DURABILITY);
    private static final Item.Properties NULLIFIER_PROPERTIES = GetBaseProperties().durability(NULLIFIER_DURABILITY);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStickItem(DEBUG_PROPERTIES.durability(1)));
    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> ATTRACTOR_MODULE = ITEMS.register("attractor_module", () -> new ItemAttractorModuleItem(GetBaseProperties().durability(ATTRACTOR_UNIT_DURABILITY)));
    public static final RegistryObject<Item> SWIFTNESS_EMITTER = ITEMS.register("swiftness_emitter", () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.MOVEMENT_SPEED, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> FIRE_RESISTANCE_EMITTER = ITEMS.register("fire_resistance_emitter", () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.FIRE_RESISTANCE, Const.EffectAmplifier.I));
    public static final RegistryObject<Item> REGENERATION_EMITTER = ITEMS.register("regeneration_emitter", () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.REGENERATION, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> NIGHT_VISION_EMITTER = ITEMS.register("night_vision_emitter", () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.NIGHT_VISION, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> WATER_BREATHING_EMITTER = ITEMS.register("water_breathing_emitter", () -> new EffectEmitterItem(EMITTER_PROPERTIES, MobEffects.WATER_BREATHING, Const.EffectAmplifier.V));
    public static final RegistryObject<Item> MOB_DNA_MODULE = ITEMS.register("mob_dna_module", () -> new MobDnaModuleItem(GetBaseProperties()));
    public static final RegistryObject<Item> BLOCK_GPS_MODULE = ITEMS.register("block_gps_module", () -> new BlockGpsModuleItem(GetBaseProperties()));
    public static final RegistryObject<Item> NANITE_REPAIR_MODULE = ITEMS.register("nanite_repair_module", () -> new NaniteRepairModuleItem(GetBaseProperties().durability(ATTRACTOR_UNIT_DURABILITY)));
    public static final RegistryObject<Item> POISON_NULLIFIER = ITEMS.register("poison_nullifier", () -> new EffectNullifierItem(NULLIFIER_PROPERTIES, MobEffects.POISON));
    public static final RegistryObject<Item> WITHER_NULLIFIER = ITEMS.register("wither_nullifier", () -> new EffectNullifierItem(NULLIFIER_PROPERTIES, MobEffects.WITHER));
    public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword", () -> new FakeSword());
    public static final RegistryObject<Item> NANITE_ENHANCED_PICKAXE = ITEMS.register("nanite_enhanced_pickaxe", () -> new UnbreakablePickAxe(Tiers.NETHERITE, 1, -2.8F, GetBaseProperties()));
    public static final RegistryObject<Item> NANITE_ENHANCED_AXE = ITEMS.register("nanite_enhanced_axe", () -> new UnbreakableAxe(Tiers.NETHERITE, 5.0F, -3.0F, GetBaseProperties()));
    public static final RegistryObject<Item> NANITE_ENHANCED_SHOVEL = ITEMS.register("nanite_enhanced_shovel", () -> new UnbreakableShovel(Tiers.NETHERITE, 5.0F, -3.0F, GetBaseProperties()));





    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(GetBaseProperties()));
    }

    public static Item.Properties GetBaseProperties() {
        return new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);
    }
}
