package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.DamagePadUpgradeItem;
import andronomos.androtech.item.FakeSword;
import andronomos.androtech.item.tools.NaniteEnhancedAxe;
import andronomos.androtech.item.tools.NaniteEnhancedPickAxe;
import andronomos.androtech.item.tools.NaniteEnhancedShovel;
import andronomos.androtech.item.tools.NaniteEnhancedSword;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	public static final Item.Properties NANITE_TOOL_PROPERTIES = new Item.Properties().fireResistant();

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AndroTech.MODID);

	public static final RegistryObject<Item> CHIP_WAFER = register("chip_wafer");
	public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
	public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
	public static final RegistryObject<Item> ELITE_CHIP = register("elite_chip");

	public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword",
			() -> new FakeSword(new Item.Properties()));

	public static final RegistryObject<Item> SHARPNESS_AUGMENT = ITEMS.register("sharpness_augment",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.SHARPNESS));

	public static final RegistryObject<Item> LOOTING_AUGMENT = ITEMS.register("looting_augment",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.MOB_LOOTING));

	public static final RegistryObject<Item> FIRE_AUGMENT = ITEMS.register("fire_augment",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.FIRE_ASPECT));

	public static final RegistryObject<Item> SMITE_AUGMENT = ITEMS.register("smite_augment",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.SMITE));

	public static final RegistryObject<Item> NANITE_ENHANCED_PICKAXE = ITEMS.register("nanite_enhanced_pickaxe",
			() -> new NaniteEnhancedPickAxe(Tiers.NETHERITE, 1, -2.8F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> NANITE_ENHANCED_AXE = ITEMS.register("nanite_enhanced_axe",
			() -> new NaniteEnhancedAxe(Tiers.NETHERITE, 5.0F, -3.0F, NANITE_TOOL_PROPERTIES));

	public static final RegistryObject<Item> NANITE_ENHANCED_SHOVEL = ITEMS.register("nanite_enhanced_shovel",
			() -> new NaniteEnhancedShovel(Tiers.NETHERITE, 5.0F, -3.0F, NANITE_TOOL_PROPERTIES));

	public static final RegistryObject<Item> NANITE_ENHANCED_SWORD = ITEMS.register("nanite_enhanced_sword",
			() -> new NaniteEnhancedSword(Tiers.NETHERITE, 3, -2.4F, NANITE_TOOL_PROPERTIES));

	private static RegistryObject<Item> register(String name) {
		return ITEMS.register(name, () -> new Item(new Item.Properties()));
	}
}
