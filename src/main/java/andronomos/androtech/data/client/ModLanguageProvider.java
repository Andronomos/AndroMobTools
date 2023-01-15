package andronomos.androtech.data.client;

import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGenerator;
import andronomos.androtech.block.machine.itemmender.ItemMender;
import andronomos.androtech.block.machine.cropfarmer.CropFarmer;
import andronomos.androtech.block.IPoweredBlock;
import andronomos.androtech.block.machine.mobcloner.MobCloner;
import andronomos.androtech.block.machine.itemattractor.ItemAttractor;
import andronomos.androtech.block.machine.itemincinerator.ItemIncinerator;
import andronomos.androtech.block.machine.redstonereceiver.RedstoneReceiver;
import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitter;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPad;
import andronomos.androtech.item.Module.BlockGPSModule;
import andronomos.androtech.item.Module.MobStasisModule;
import andronomos.androtech.registry.ModBlocks;
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

		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String name = ForgeRegistries.BLOCKS.getKey(b).getPath();
			name = name.replaceAll("_", " ");
			name = WordUtils.capitalize(name);
			add(b, name);
		});

		add(ModItems.CHIP_WAFER.get(), "Chip Wafer");
		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ModItems.ELITE_CHIP.get(), "Elite Chip");
		add(ModItems.MOB_STASIS_MODULE.get(), "Mob Stasis Module");
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE, "Location");
		add(BlockGPSModule.TOOLTIP_BLOCK_GPS_MODULE_COORDS, "X: %1$s Y: %2$s Z: %3$s");
		add(BlockGPSModule.BLOCK_GPS_MODULE_SAVED, "Saved Block Position %1$s");
		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_MOB, "Mob: %1$s");
		add(MobStasisModule.TOOLTIP_MOB_STASIS_MODULE_HEALTH, "Health: %1$s");
		add(ModItems.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		add(ModItems.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		add(ModItems.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");
		add(ModItems.NANITE_ENHANCED_SWORD.get(), "Nanite Enhanced Sword");
		add(ModItems.ITEM_ATTRACTOR_MODULE.get(), "Item Attractor Module");
		add(ModItems.MENDING_MODULE.get(), "Mending Module");

		add(CropFarmer.DISPLAY_NAME, "Crop Farmer");
		add(CropFarmer.TOOLTIP, "Automatically harvests most crops and fertilizes soil");

		add(MobCloner.DISPLAY_NAME, "Mob Cloner");
		add(MobCloner.TOOLTIP, "Spawns copies of mobs placed inside it");

		add(ItemAttractor.DISPLAY_NAME, "Item Attractor");
		add(ItemAttractor.TOOLTIP, "Pulls nearby items");

		add(ItemIncinerator.DISPLAY_NAME, "Item Attractor");
		add(ItemIncinerator.TOOLTIP, "Pulls nearby items");

		add(RedstoneTransmitter.DISPLAY_NAME, "Redstone Transmitter");
		add(RedstoneTransmitter.TOOLTIP, "Sends a wireless redstone signal");

		add(RedstoneReceiver.DISPLAY_NAME, "Redstone Receiver");
		add(RedstoneReceiver.TOOLTIP, "Receives a wireless redstone signal");

		add(MobKillingPad.DISPLAY_NAME, "Mob Killing Pad");

		add(ItemMender.DISPLAY_NAME, "Item Mender");
		add(ItemMender.TOOLTIP, "Repairs damaged items");

		add(CreativeEnergyGenerator.DISPLAY_NAME, "Creative Energy Generator");
		add(CreativeEnergyGenerator.TOOLTIP, "Infinite energy source");

		add(IPoweredBlock.GUI_ON, "Powered On");
		add(IPoweredBlock.GUI_OFF, "Powered Off");
	}
}
