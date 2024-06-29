package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.entityvacuum.EntityVacuumMenu;
import andronomos.androtech.block.entityrepulsor.EntityRepulsorMenu;
import andronomos.androtech.block.mobkiller.MobKillerMenu;
import andronomos.androtech.block.itemincinerator.ItemIncineratorMenu;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AndroTech.MODID);

	public static final RegistryObject<MenuType<MobKillerMenu>> MOB_KILLER_MENU = register(MobKillerMenu::new, "mob_killer_menu");
	public static final RegistryObject<MenuType<EntityVacuumMenu>> ENTITY_VACUUM_MENU = register(EntityVacuumMenu::new, "entity_vacuum_menu");
	public static final RegistryObject<MenuType<ItemIncineratorMenu>> ITEM_INCINERATOR_MENU = register(ItemIncineratorMenu::new, "item_incinerator_menu");
	public static final RegistryObject<MenuType<RedstoneSignalTransmitterMenu>> REDSTONE_SIGNAL_TRANSMITTER_MENU = register(RedstoneSignalTransmitterMenu::new, "redstone_signal_transmitter_menu");
	public static final RegistryObject<MenuType<EntityRepulsorMenu>> ENTITY_REPULSOR_MENU = register(EntityRepulsorMenu::new, "entity_repulsor_menu");

	public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String name) {
		return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
	}
}
