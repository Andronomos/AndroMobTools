package andronomos.androtech.data.client;

import andronomos.androtech.item.Module.BlockGPSModule;
import andronomos.androtech.item.Module.MobStasisModule;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import static andronomos.androtech.AndroTech.TAB_NAME;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen, String locale) {
		super(gen, andronomos.androtech.AndroTech.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + TAB_NAME, "AndroTech");

		ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(i -> {
			String name = ForgeRegistries.ITEMS.getKey(i).getPath();
			name = name.replaceAll("_", " ");
			name = WordUtils.capitalize(name);
			add(i, name);
		});

		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE, "Location");
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE_COORDS, "X: %1$s Y: %2$s Z: %3$s");
		add(BlockGPSModule.BLOCK_GPS_MODULE_SAVED, "Saved Block Position %1$s");

		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_MOB, "Mob: %1$s");
		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_HEALTH, "Health: %1$s");
	}
}
