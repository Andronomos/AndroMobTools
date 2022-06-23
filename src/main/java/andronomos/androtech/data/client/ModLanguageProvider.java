package andronomos.androtech.data.client;

import andronomos.androtech.block.ItemIncinerator.ItemIncinerator;
import andronomos.androtech.block.amethystharvester.AmethystHarvester;
import andronomos.androtech.block.animalfarmer.AnimalFarmer;
import andronomos.androtech.block.blockminer.BlockMiner;
import andronomos.androtech.block.cropfarmer.CropFarmer;
import andronomos.androtech.block.itemattractor.ItemAttractor;
import andronomos.androtech.block.itemmender.ItemMender;
import andronomos.androtech.block.mobcloner.MobCloner;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPad;
import andronomos.androtech.block.redstonetransmitter.RedstoneTransmitter;
import andronomos.androtech.item.BlockGpsRecorder;
import andronomos.androtech.item.FluidRemover;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import static andronomos.androtech.AndroTech.TAB_NAME;
import static andronomos.androtech.item.MobCloningModule.TOOLTIP_MOB_CLONING_MODULE_HEALTH;
import static andronomos.androtech.item.MobCloningModule.TOOLTIP_MOB_CLONING_MODULE_MOB;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen, String locale) {
		super(gen, andronomos.androtech.AndroTech.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + TAB_NAME, "AndroTech");

		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String name = b.getRegistryName().getPath();
			name = name.replaceAll("_", " ");
			name = WordUtils.capitalize(name);
			add(b, name);
		});

		add(ModItems.MOB_CLONING_MODULE.get(), "Mob Cloning Module");
		add(TOOLTIP_MOB_CLONING_MODULE_MOB, "Mob: ");
		add(TOOLTIP_MOB_CLONING_MODULE_HEALTH, "Health: ");
		add(ModItems.BLOCK_GPS_RECORDER.get(), "Block GPS Recorder");
		add(BlockGpsRecorder.TOOLTIP_BLOCK_GPS_MODULE, "Location");
		add(BlockGpsRecorder.TOOLTIP_BLOCK_GPS_MODULE_X, "X: ");
		add(BlockGpsRecorder.TOOLTIP_BLOCK_GPS_MODULE_Y, "Y: ");
		add(BlockGpsRecorder.TOOLTIP_BLOCK_GPS_MODULE_Z, "Z: ");
		add(BlockGpsRecorder.BLOCK_GPS_MODULE_SAVED, "Saved Block Position ");
		add(ModItems.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		add(ModItems.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		add(ModItems.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");
		add(ModItems.NANITE_ENHANCED_SWORD.get(), "Nanite Enhanced Sword");
		add(ModItems.PORTABLE_ITEM_ATTRACTOR.get(), "Portable Item Attractor");
		add(ModItems.PORTABLE_ITEM_MENDER.get(), "Portable Item Mender");
		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ModItems.NIGHT_VISION_EMITTER.get(), "Night Vision Emitter");
		add(ModItems.SWIFTNESS_EMITTER.get(), "Swiftness Emitter");
		add(ModItems.FIRE_RESISTANCE_EMITTER.get(), "Fire Resistance Emitter");
		add(ModItems.WATER_BREATHING_EMITTER.get(), "Water Breathing Emitter");
		add(ModItems.REGENERATION_EMITTER.get(), "Regeneration Emitter");
		add(ModItems.POISON_NULLIFIER.get(), "Poison Nullifier");
		add(ModItems.WITHER_NULLIFIER.get(), "Wither Nullifier");
		add(ModItems.FLUID_REMOVER.get(), "Fluid Remover");
		add(FluidRemover.FLUID_REMOVER_MODE, "Mode: ");

		add(RedstoneTransmitter.name, "Redstone Transmitter");
		add(MobCloner.name, "Mob Cloner");
		add(CropFarmer.name, "Crop Farmer");
		add(ItemMender.name, "Item Mender");
		add(MobKillingPad.name, "Mob Killing Pad");
		add(ItemIncinerator.name, "Item Incinerator");
		add(ItemAttractor.name, "Item Attractor");
		add(AnimalFarmer.name, "Animal Farmer");
		add(AmethystHarvester.name, "Amethyst Harvester");
		add(BlockMiner.name, "Block Miner");

		add("gui.androtech.powered_on", "Powered On");
		add("gui.androtech.powered_off", "Powered Off");
	}
}
