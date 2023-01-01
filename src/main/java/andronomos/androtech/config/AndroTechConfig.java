package andronomos.androtech.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class AndroTechConfig {
	public static final ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.ConfigValue<Integer> ATTRACTOR_MODULE_RANGE;
	public static ForgeConfigSpec.ConfigValue<Boolean> ATTRACTOR_MODULE_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_REPAIR_RATE;
	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Boolean> MOB_STASIS_MODULE_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> MOB_STASIS_MODULE_DURABILITY;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		CONFIG = configBuilder.build();
	}

	public static void setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Mending Module");
		MENDING_MODULE_REPAIR_RATE = builder.comment("Repair Value").define("repair_value", 10);
		MENDING_MODULE_DURABILITY = builder.comment("Durability").define("durability", 10065);
		builder.pop();

		builder.push("Attractor Module");
		ATTRACTOR_MODULE_RANGE = builder.comment("The distance in blocks the attractor module will pull items").define("range", 10);
		ATTRACTOR_MODULE_TAKE_DAMAGE = builder.comment("Enable damage").define("take_damage", true);
		builder.pop();

		builder.push("Mob Stasis Module");
		MOB_STASIS_MODULE_TAKE_DAMAGE = builder.comment("Enable damage").define("take_damage", false);
		MOB_STASIS_MODULE_DURABILITY = builder.comment("Durability").define("durability", 1);
		builder.pop();
	}

	public static void loadConfig(Path path)
	{
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		CONFIG.setConfig(configData);
	}
}
