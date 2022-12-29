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
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE_X, "X: ");
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE_Y, "Y: ");
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE_Z, "Z: ");
		add(BlockGPSModule.BLOCK_GPS_MODULE_SAVED, "Saved Block Position ");

		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_MOB, "Mob: ");
		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_HEALTH, "Health: ");
	}
}
