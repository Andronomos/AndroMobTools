package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.FakeSword;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegistry {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AndroTech.MODID);

	public static final String BASETABNAME = "androtech";

	public static final RegistryObject<CreativeModeTab> BLOCKPALETTE_TAB = CREATIVE_MODE_TABS.register(BASETABNAME, () -> CreativeModeTab.builder()
			.title(Component.translatable("creativetab." + BASETABNAME))
			.icon(BlockRegistry.WEAK_ACCELERATION_PAD.get().asItem()::getDefaultInstance)
			.displayItems((parameters, output) -> {
				BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
					output.accept(b);
				});

				ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(i -> {
					if(i instanceof FakeSword) {
						return;
					}

					output.accept(i);
				});
			}).build());
}
