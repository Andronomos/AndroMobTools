package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.itemattractor.ItemAttractorMenu;
import andronomos.androtech.block.pad.damagepad.DamagePadMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AndroTech.MODID);

	public static final RegistryObject<MenuType<DamagePadMenu>> DAMAGE_PAD_MENU = register(DamagePadMenu::new, "damage_pad_menu");
	public static final RegistryObject<MenuType<ItemAttractorMenu>> ITEM_ATTRACTOR_MENU = register(ItemAttractorMenu::new, "item_attractor_menu");

	public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String name) {
		return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
	}
}
