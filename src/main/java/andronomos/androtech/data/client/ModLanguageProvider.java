package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.MachineBlock;
import andronomos.androtech.block.itemattractor.ItemAttractorBlock;
import andronomos.androtech.block.damagepad.DamagePadBlock;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlock;
import andronomos.androtech.block.wirelessredstone.RedstoneSignalReceiverBlock;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlock;
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

		add(DamagePadBlock.DISPLAY_NAME, "Damage Pad");
		add(DamagePadBlock.TOOLTIP, "Damages entities that stand on it");
		add(ItemAttractorBlock.DISPLAY_NAME, "Item Attractor");
		add(ItemAttractorBlock.TOOLTIP, "Pulls nearby items");
		add(ItemIncineratorBlock.DISPLAY_NAME, "Item Incinerator");
		add(ItemIncineratorBlock.TOOLTIP, "Destroys items placed inside it");
		add(RedstoneSignalReceiverBlock.DISPLAY_NAME, "Redstone Signal Receiver");
		add(RedstoneSignalReceiverBlock.TOOLTIP, "Emits a redstone signal when powered by a signal transmitter");
		add(RedstoneSignalTransmitterBlock.DISPLAY_NAME, "Redstone Signal Transmitter");
		add(RedstoneSignalTransmitterBlock.TOOLTIP, "Transmits a redstone signal to a receiver");

		add(ItemRegistry.FIRE_AUGMENT.get(), "Fire Aspect Augment");
		//add(ItemRegistry.SMITE_AUGMENT.get(), "Smite Augment");
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

		//add(ItemRegistry.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		//add(ItemRegistry.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		//add(ItemRegistry.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");
		//add(ItemRegistry.NANITE_ENHANCED_SWORD.get(), "Nanite Enhanced Sword");

		add(ItemRegistry.BASIC_CHIP.get(), "Basic Chip");
		add(ItemRegistry.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ItemRegistry.ELITE_CHIP.get(), "Elite Chip");

		add(MachineBlock.GUI_ON, "Powered On");
		add(MachineBlock.GUI_OFF, "Powered Off");
	}

	static String capitalizeWords(String input) {
		return Arrays.stream(input.split("\\s+"))
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
				.collect(Collectors.joining(" "));
	}
}
