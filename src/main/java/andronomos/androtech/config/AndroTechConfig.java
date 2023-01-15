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

	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_PICKAXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_AXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SHOVEL_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SWORD_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_REPAIR_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> CROP_FARMER_ENERGY_CAPACITY;
	public static ForgeConfigSpec.ConfigValue<Integer> CROP_FARMER_ENERGY_TRANSFER_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> ITEM_ATTRACTOR_ENERGY_CAPACITY;
	public static ForgeConfigSpec.ConfigValue<Integer> ITEM_ATTRACTOR_ENERGY_TRANSFER_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> ITEM_MENDER_ENERGY_CAPACITY;
	public static ForgeConfigSpec.ConfigValue<Integer> ITEM_MENDER_ENERGY_TRANSFER_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> MOB_CLONER_ENERGY_CAPACITY;
	public static ForgeConfigSpec.ConfigValue<Integer> MOB_CLONER_ENERGY_TRANSFER_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> CREATIVE_ENERGY_GENERATOR_ENERGY_CAPACITY;
	public static ForgeConfigSpec.ConfigValue<Integer> CREATIVE_ENERGY_GENERATOR_ENERGY_TRANSFER_RATE;

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

		builder.push("Nanite Tools");
		NANITE_PICKAXE_DURABILITY = builder.comment("Nanite Pickaxe Durability").define("durability", 8124);
		NANITE_AXE_DURABILITY = builder.comment("Nanite Axe Durability").define("durability", 8124);
		NANITE_SHOVEL_DURABILITY = builder.comment("Nanite Shovel Durability").define("durability", 8124);
		NANITE_SWORD_DURABILITY = builder.comment("Nanite Sword Durability").define("durability", 8124);
		NANITE_REPAIR_RATE = builder.comment("Repair Rate").define("repair_rate", 10);
		builder.pop();

		builder.push("Crop Farmer");
		CROP_FARMER_ENERGY_CAPACITY = builder.comment("Max energy capacity").define("energy_capacity", 60000);
		CROP_FARMER_ENERGY_TRANSFER_RATE = builder.comment("Max energy transfer per tick").define("energy_transfer_rate", 256);
		builder.pop();

		builder.push("Item Attractor");
		ITEM_ATTRACTOR_ENERGY_CAPACITY = builder.comment("Max energy capacity").define("energy_capacity", 60000);
		ITEM_ATTRACTOR_ENERGY_TRANSFER_RATE = builder.comment("Max energy transfer per tick").define("energy_transfer_rate", 256);
		builder.pop();

		builder.push("Item Mender");
		ITEM_MENDER_ENERGY_CAPACITY = builder.comment("Max energy capacity").define("energy_capacity", 60000);
		ITEM_MENDER_ENERGY_TRANSFER_RATE = builder.comment("Max energy transfer per tick").define("energy_transfer_rate", 256);
		builder.pop();

		builder.push("Mob Cloner");
		MOB_CLONER_ENERGY_CAPACITY = builder.comment("Max energy capacity").define("energy_capacity", 60000);
		MOB_CLONER_ENERGY_TRANSFER_RATE = builder.comment("Max energy transfer per tick").define("energy_transfer_rate", 256);
		builder.pop();

		builder.push("Creative Energy Generator");
		CREATIVE_ENERGY_GENERATOR_ENERGY_CAPACITY = builder.comment("Max energy capacity").define("energy_capacity", 999999999);
		CREATIVE_ENERGY_GENERATOR_ENERGY_TRANSFER_RATE = builder.comment("Max energy transfer per tick").define("energy_transfer_rate", 1000000);
		builder.pop();
	}

	public static void loadConfig(Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		CONFIG.setConfig(configData);
	}
}
