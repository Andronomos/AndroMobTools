package andronomos.androtech.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class AndroTechConfig {
	public static final ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.ConfigValue<Integer> ENTITY_VACUUM_RANGE;
	public static ForgeConfigSpec.ConfigValue<Integer> ENTITY_VACUUM_XP_CAPACITY;

	public static ForgeConfigSpec.ConfigValue<Integer> DEVICE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Boolean> DEVICE_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> TICKING_DEVICE_DURABILITY;

	public static ForgeConfigSpec.ConfigValue<Integer> PORTABLE_ENTITY_VACUUM_RANGE;
	public static ForgeConfigSpec.ConfigValue<Boolean> PORTABLE_ENTITY_VACUUM_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> PORTABLE_ENTITY_VACUUM_DAMAGE_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_REPAIR_RATE;
	public static ForgeConfigSpec.ConfigValue<Integer> MENDING_MODULE_DURABILITY;

	public static ForgeConfigSpec.ConfigValue<Boolean> MOB_STORAGE_DEVICE_TAKE_DAMAGE;
	public static ForgeConfigSpec.ConfigValue<Integer> MOB_STORAGE_DEVICE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> MOB_STORAGE_DEVICE_DAMAGE_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_PICKAXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_AXE_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SHOVEL_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_SWORD_DURABILITY;
	public static ForgeConfigSpec.ConfigValue<Integer> NANITE_REPAIR_RATE;

	public static ForgeConfigSpec.ConfigValue<Integer> FLUID_EVAPORATOR_RANGE;
	public static ForgeConfigSpec.ConfigValue<Integer> FLUID_EVAPORATOR_DURABILITY;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		CONFIG = configBuilder.build();
	}

	public static void setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Entity Vacuum");
		ENTITY_VACUUM_RANGE = builder.comment("Distance in blocks in each direction to search for items").define("range", 4);
		ENTITY_VACUUM_XP_CAPACITY = builder.comment("How many bucket of liquid xp the vacuum can hold").define("xp_capacity", 16);
		builder.pop();



		builder.push("General");
		DEVICE_TAKE_DAMAGE = builder.comment("Do devices without a specific config option take damage").define("take_damage", false);
		DEVICE_DURABILITY = builder.comment("Default durability for devices").define("durability", 50);
		TICKING_DEVICE_DURABILITY = builder.comment("Default durability for devices that tick").define("tick_durability", 1000);
		builder.pop();

		//builder.push("Mending Module");
		//MENDING_MODULE_REPAIR_RATE = builder.comment("The amount of durability to restore per cycle").define("repair_value", 10);
		//MENDING_MODULE_DURABILITY = builder.comment("Durability").define("durability", 1000);
		//builder.pop();

		builder.push("Item Attraction Emitter");
		PORTABLE_ENTITY_VACUUM_RANGE = builder.comment("Range").define("range", 10);
		PORTABLE_ENTITY_VACUUM_TAKE_DAMAGE = builder.comment("Take damage").define("take_damage", false);
		PORTABLE_ENTITY_VACUUM_DAMAGE_RATE = builder.comment("Damage per item").define("damage_rate", 1);
		builder.pop();

		builder.push("Mob Storage Device");
		MOB_STORAGE_DEVICE_TAKE_DAMAGE = builder.comment("Take damage").define("take_damage", true);
		MOB_STORAGE_DEVICE_DURABILITY = builder.comment("Durability").define("durability", 10);
		MOB_STORAGE_DEVICE_DAMAGE_RATE = builder.comment("Damage per use").define("damage_rate", 1);
		builder.pop();

		builder.push("Nanite Tools");
		NANITE_PICKAXE_DURABILITY = builder.comment("Nanite Pickaxe Durability").define("pickaxe_durability", 8124);
		NANITE_AXE_DURABILITY = builder.comment("Nanite Axe Durability").define("axe_durability", 8124);
		NANITE_SHOVEL_DURABILITY = builder.comment("Nanite Shovel Durability").define("shovel_durability", 8124);
		NANITE_SWORD_DURABILITY = builder.comment("Nanite Sword Durability").define("sword_durability", 8124);
		NANITE_REPAIR_RATE = builder.comment("Repair Rate").define("repair_rate", 10);
		builder.pop();

		builder.push("Fluid Evaporator");
		FLUID_EVAPORATOR_RANGE = builder.comment("How many blocks around the player the Fluid Evaporator will look for fluid").define("range", 8);
		FLUID_EVAPORATOR_DURABILITY = builder.comment("How many blocks around the player the Fluid Evaporator will look for fluid").define("durability", 1024);
		builder.pop();
	}

	public static void loadConfig(Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		configData.load();
		CONFIG.setConfig(configData);
	}
}
