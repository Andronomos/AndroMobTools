package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.DamagePadUpgradeItem;
import andronomos.androtech.item.FakeSword;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AndroTech.MODID);

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

	public static final RegistryObject<Item> ARTHRO_AUGMENT = ITEMS.register("arthro_augment",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.BANE_OF_ARTHROPODS));
}
