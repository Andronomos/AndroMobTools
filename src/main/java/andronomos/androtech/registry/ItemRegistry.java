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

	public static final RegistryObject<Item> DAMAGE_PAD_UPGRADE_SHARPNESS = ITEMS.register("damage_pad_upgrade_sharpness",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.SHARPNESS));

	public static final RegistryObject<Item> DAMAGE_PAD_UPGRADE_LOOTING = ITEMS.register("damage_pad_upgrade_looting",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.MOB_LOOTING));

	public static final RegistryObject<Item> DAMAGE_PAD_UPGRADE_FIRE = ITEMS.register("damage_pad_upgrade_fire",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.FIRE_ASPECT));

	public static final RegistryObject<Item> DAMAGE_PAD_UPGRADE_SMITE = ITEMS.register("damage_pad_upgrade_smite",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.SMITE));

	public static final RegistryObject<Item> DAMAGE_PAD_UPGRADE_ARTHRO = ITEMS.register("damage_pad_upgrade_arthro",
			() -> new DamagePadUpgradeItem(new Item.Properties(), Enchantments.BANE_OF_ARTHROPODS));
}
