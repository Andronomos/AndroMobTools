package andronomos.androtech.data.client;

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
	}
}
