package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModCreativeTabs;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ModLanguageProvider extends LanguageProvider {
	public ModLanguageProvider(PackOutput output, String locale) {
		super(output, AndroTech.MODID, locale);
	}

	@Override
	protected void addTranslations() {
		add("creativetab." + ModCreativeTabs.BASETABNAME, "AndroTech");

		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)).getPath();
			name = name.replaceAll("_", " ");
			name = StringUtils.capitalize(name);
			add(b, name);
		});
	}
}
