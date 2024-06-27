package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.TeleportInhibitorBlock;
import andronomos.androtech.block.itemattractor.EntityVacuumBlock;
import andronomos.androtech.block.mobrepulsor.MobRepulsorBlock;
import andronomos.androtech.block.mobkiller.MobKillerBlock;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlock;
import andronomos.androtech.block.wirelessredstone.RedstoneSignalReceiverBlock;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlock;
import andronomos.androtech.inventory.client.PowerButton;
import andronomos.androtech.inventory.client.RenderOutlineButton;
import andronomos.androtech.item.FluidEvaporator;
import andronomos.androtech.item.GPSRecorderItem;
import andronomos.androtech.item.MobStorageDevice;
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

		add(MobKillerBlock.DISPLAY_NAME, "Mob Killer");
		add(MobKillerBlock.TOOLTIP, "Damages entities in-front of it");
		add(EntityVacuumBlock.DISPLAY_NAME, "Entity Vacuum");
		add(EntityVacuumBlock.TOOLTIP, "Vacuums up nearby items and experience");
		add(EntityVacuumBlock.AMOUNT_TOOLTIP, "%s / %s mB");
		add(ItemIncineratorBlock.DISPLAY_NAME, "Item Incinerator");
		add(ItemIncineratorBlock.TOOLTIP, "Destroys items placed inside it");
		add(RedstoneSignalReceiverBlock.DISPLAY_NAME, "Redstone Signal Receiver");
		add(RedstoneSignalReceiverBlock.TOOLTIP, "Emits a redstone signal when powered by a signal transmitter");
		add(RedstoneSignalTransmitterBlock.DISPLAY_NAME, "Redstone Signal Transmitter");
		add(RedstoneSignalTransmitterBlock.TOOLTIP, "Transmits a redstone signal to a receiver");
		add(MobRepulsorBlock.DISPLAY_NAME, "Mob Repulsor");
		add(MobRepulsorBlock.TOOLTIP, "Pushes entities in-front of it away");
		add(TeleportInhibitorBlock.DISPLAY_NAME, "Teleport Inhibitor");
		add(TeleportInhibitorBlock.TOOLTIP, "Prevents Enderman from teleporting in an area");

		add("fluid_type.androtech.liquid_xp", "Liquid XP");

		add(ItemRegistry.FIRE_AUGMENT.get(), "Fire Aspect Augment");
		add(ItemRegistry.SHARPNESS_AUGMENT.get(), "Sharpness Augment");
		add(ItemRegistry.LOOTING_AUGMENT.get(), "Looting Augment");
		add(ItemRegistry.GPS_RECORDER.get(), "GPS Recorder");
		add(ItemRegistry.ITEM_ATTRACTION_EMITTER.get(), "Item Attraction Emitter");
		add(GPSRecorderItem.TOOLTIP_GPS_MODULE, "Location");
		add(GPSRecorderItem.TOOLTIP_GPS_MODULE_COORDS, "X: %1$s Y: %2$s Z: %3$s");
		add(GPSRecorderItem.GPS_MODULE_SAVED, "Saved Block Position %1$s");
		add(MobStorageDevice.TOOLTIP_MOB_STORAGE_DEVICE_MOB, "Mob: %1$s");
		add(MobStorageDevice.TOOLTIP_MOB_STORAGE_DEVICE_HEALTH, "Health: %1$s");
		add(ItemRegistry.MOB_STORAGE_DEVICE.get(), "Mob Storage Device");
		add(ItemRegistry.FLUID_EVAPORATOR.get(), "Fluid Evaporator");
		add(FluidEvaporator.TOOLTIP, "Evaporates fluid in a radius around the player");
		add(FluidEvaporator.TOOLTIP_MODE, "Fluid Mode: %s");
		add(FluidEvaporator.TOOLTIP_MODE_WATER, "Water");
		add(FluidEvaporator.TOOLTIP_MODE_LAVA, "Lava");

		//add(ItemRegistry.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		//add(ItemRegistry.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		//add(ItemRegistry.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");
		//add(ItemRegistry.NANITE_ENHANCED_SWORD.get(), "Nanite Enhanced Sword");

		add(ItemRegistry.BASIC_CHIP.get(), "Basic Chip");
		add(ItemRegistry.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ItemRegistry.ELITE_CHIP.get(), "Elite Chip");
		add(ItemRegistry.REPULSOR_WIDTH_UPGRADE.get(), "Repulsor Width Upgrade");
		add(ItemRegistry.REPULSOR_HEIGHT_UPGRADE.get(), "Repulsor Height Upgrade");
		add(ItemRegistry.REPULSOR_DISTANCE_UPGRADE.get(), "Repulsor Distance Upgrade");
		add(ItemRegistry.LIQUID_XP_BUCKET.get(), "Liquid XP Bucket");

		add(PowerButton.TOOLTIP_ON, "Powered On");
		add(PowerButton.TOOLTIP_OFF, "Powered Off");
		add(RenderOutlineButton.TOOLTIP_ON, "Overlay On");
		add(RenderOutlineButton.TOOLTIP_OFF, "Overlay Off");

		add("block.androtech.weak_acceleration_pad.tooltip", "Accelerates any mob or player that steps on it");
		add("block.androtech.normal_acceleration_pad.tooltip", "Accelerates any mob or player that steps on it");
		add("block.androtech.strong_acceleration_pad.tooltip", "Accelerates any mob or player that steps on it");
	}

	static String capitalizeWords(String input) {
		return Arrays.stream(input.split("\\s+"))
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
				.collect(Collectors.joining(" "));
	}
}
