package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.pad.damagepad.DamagePadBlock;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.CreativeTabRegistry;
import andronomos.androtech.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModLanguageProvider extends LanguageProvider {
	public ModLanguageProvider(PackOutput output, String locale) {
		super(output, AndroTech.MODID, locale);
	}

	@Override
	protected void addTranslations() {
		BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)).getPath();
			name = name.replaceAll("_", " ");
			name = capitalizeWords(name);
			add(b, name);
		});

		add("creativetab." + CreativeTabRegistry.BASETABNAME, "AndroTech");

		add(DamagePadBlock.DISPLAY_NAME, "Damage Pad");
		add(DamagePadBlock.TOOLTIP, "Damages entities that stand on it");
		add(ItemRegistry.FIRE_AUGMENT.get(), "Fire Aspect Augment");
		add(ItemRegistry.SMITE_AUGMENT.get(), "Smite Augment");
		add(ItemRegistry.SHARPNESS_AUGMENT.get(), "Sharpness Augment");
		add(ItemRegistry.LOOTING_AUGMENT.get(), "Looting Augment");
	}

	static String capitalizeWords(String input) {
		return Arrays.stream(input.split("\\s+"))
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
				.collect(Collectors.joining(" "));
	}
}
