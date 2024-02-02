package andronomos.androtech.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class AndroTechConfig {
	public static final ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.ConfigValue<Integer> ITEM_ATTRACTION_EMITTER_RANGE;
	public static ForgeConfigSpec.ConfigValue<Boolean> ITEM_ATTRACTION_EMITTER_TAKE_DAMAGE;

	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_REPAIR_RATE;
	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_DURABILITY;

	public static ForgeConfigSpec.ConfigValue<Boolean> MOB_STORAGE_DEVICE_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> MOB_STORAGE_DEVICE_DURABILITY;

	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_PICKAXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_AXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SHOVEL_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SWORD_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_REPAIR_RATE;

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
		ITEM_ATTRACTION_EMITTER_RANGE = builder.comment("The distance in blocks the attractor module will pull items").define("range", 10);
		ITEM_ATTRACTION_EMITTER_TAKE_DAMAGE = builder.comment("Enable damage").define("take_damage", true);
		builder.pop();

		builder.push("Mob Capture Device");
		MOB_STORAGE_DEVICE_TAKE_DAMAGE = builder.comment("Enable damage").define("take_damage", false);
		MOB_STORAGE_DEVICE_DURABILITY = builder.comment("Durability").define("durability", 1);
		builder.pop();

		builder.push("Nanite Tools");
		NANITE_PICKAXE_DURABILITY = builder.comment("Nanite Pickaxe Durability").define("durability", 8124);
		NANITE_AXE_DURABILITY = builder.comment("Nanite Axe Durability").define("durability", 8124);
		NANITE_SHOVEL_DURABILITY = builder.comment("Nanite Shovel Durability").define("durability", 8124);
		NANITE_SWORD_DURABILITY = builder.comment("Nanite Sword Durability").define("durability", 8124);
		NANITE_REPAIR_RATE = builder.comment("Repair Rate").define("repair_rate", 10);
		builder.pop();
	}

	public static void loadConfig(Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		CONFIG.setConfig(configData);
	}
}
