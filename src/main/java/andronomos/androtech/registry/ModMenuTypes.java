package andronomos.androtech.registry;

import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGeneratorMenu;
import andronomos.androtech.block.machine.itemmender.ItemMenderMenu;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerMenu;
import andronomos.androtech.block.machine.mobcloner.MobClonerMenu;
import andronomos.androtech.block.machine.itemattractor.ItemAttractorMenu;
import andronomos.androtech.block.machine.itemincinerator.ItemIncineratorMenu;
import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitterMenu;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, andronomos.androtech.AndroTech.MOD_ID);

	public static final RegistryObject<MenuType<CropFarmerMenu>> CROP_FARMER = register(CropFarmerMenu::new, "crop_farmer_menu");
	public static final RegistryObject<MenuType<MobClonerMenu>> MOB_CLONER = register(MobClonerMenu::new, "mob_cloner_menu");
	public static final RegistryObject<MenuType<ItemAttractorMenu>> ITEM_ATTRACTOR = register(ItemAttractorMenu::new, "item_attractor_menu");
	public static final RegistryObject<MenuType<ItemIncineratorMenu>> ITEM_INCINERATOR = register(ItemIncineratorMenu::new, "item_incinerator_menu");
	public static final RegistryObject<MenuType<RedstoneTransmitterMenu>> REDSTONE_TRANSMITTER = register(RedstoneTransmitterMenu::new, "redstone_transmitter_menu");
	public static final RegistryObject<MenuType<MobKillingPadMenu>> MOB_KILLING_PAD = register(MobKillingPadMenu::new, "mob_killing_pad_menu");
	public static final RegistryObject<MenuType<ItemMenderMenu>> ITEM_MENDER = register(ItemMenderMenu::new, "item_mender_menu");
	public static final RegistryObject<MenuType<CreativeEnergyGeneratorMenu>> CREATIVE_ENERGY_GENERATOR = register(CreativeEnergyGeneratorMenu::new, "creative_energy_generator_menu");



	public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String name) {
		return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
	}
}
