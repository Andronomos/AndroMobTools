package andronomos.androtech.data.client;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static andronomos.androtech.AndroTech.TAB_NAME;
import static andronomos.androtech.block.CropHarvesterBlock.SCREEN_CROP_HARVESTER;
import static andronomos.androtech.block.MobClonerBlock.SCREEN_MOB_CLONER;
import static andronomos.androtech.block.ItemAttractorBlock.SCREEN_ITEM_ATTRACTOR;
import static andronomos.androtech.block.ItemIncineratorBlock.SCREEN_ITEM_INCINERATOR;
import static andronomos.androtech.block.RedstoneTransmitterBlock.SCREEN_REDSTONE_TRANSMITTER;
import static andronomos.androtech.block.pad.MobKillingPadBlock.SCREEN_MOB_KILLING_PAD;
//import static andronomos.androtech.item.BackpackItem.SCREEN_BACKPACK;
import static andronomos.androtech.item.GpsUnitItem.*;
import static andronomos.androtech.item.MobDnaModuleItem.*;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen, String locale) {
		super(gen, andronomos.androtech.AndroTech.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + TAB_NAME, "AndroTech");

		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ModItems.WITHERED_BONE.get(), "Withered Bone");

		add(ModItems.MOB_DNA_MODULE.get(), "Mob DNA Module");
		add(TOOLTIP_MOB_DNA_MODULE_MOB, "Mob: ");
		add(TOOLTIP_MOB_DNA_MODULE_HEALTH, "Health: ");

		add(ModItems.BLOCK_GPS_MODULE.get(), "Block GPS Module");
		add(TOOLTIP_BLOCK_GPS_MODULE, "Location");
		add(TOOLTIP_BLOCK_GPS_MODULE_X, "X: ");
		add(TOOLTIP_BLOCK_GPS_MODULE_Y, "Y: ");
		add(TOOLTIP_BLOCK_GPS_MODULE_Z, "Z: ");

		add(ModItems.ATTRACTOR_MODULE.get(), "Attractor Module");
		add(ModItems.SWIFTNESS_MODULE.get(), "Swiftness Module");
		add(ModItems.FIRE_RESISTANCE_MODULE.get(), "Fire Resistance Module");
		add(ModItems.REGENERATION_MODULE.get(), "Regeneration Module");
		add(ModItems.POISON_NULLIFIER.get(), "Poison Nullifier");
		add(ModItems.WITHER_NULLIFIER.get(), "Wither Nullifier");
		add(ModItems.NANITE_REPAIR_MODULE.get(), "Nanite Repair Module");
		add(ModItems.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		add(ModItems.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		add(ModItems.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");


		/** Blocks **/
		add(ModBlocks.MOB_CLONER.get(), "Mob Cloner");
		add(SCREEN_MOB_CLONER, "Mob Cloner");

		add(ModBlocks.ITEM_ATTRACTOR.get(), "Item Attractor");
		add(SCREEN_ITEM_ATTRACTOR, "Item Attractor");

		add(ModBlocks.ITEM_INCINERATOR.get(), "Item Incinerator");
		add(SCREEN_ITEM_INCINERATOR, "Item Incinerator");

		add(ModBlocks.MOB_KILLING_PAD.get(), "Mob Killing Pad");
		add(SCREEN_MOB_KILLING_PAD, "Mob Killing Pad");

		add(ModBlocks.WEAK_ACCELERATION_PAD.get(), "Weak Acceleration Pad");
		add(ModBlocks.STRONG_ACCELERATION_PAD.get(), "Strong Acceleration Pad");

		add(ModBlocks.CROP_HARVESTER.get(), "Crop Harvester");
		add(SCREEN_CROP_HARVESTER, "Crop Harvester");

		add(ModBlocks.WIRELESS_LIGHT.get(), "Wireless Light");

		add(ModBlocks.REDSTONE_TRANSMITTER.get(), "Redstone Transmitter");
		add(SCREEN_REDSTONE_TRANSMITTER, "Redstone Transmitter");

		add(ModBlocks.REDSTONE_RECEIVER.get(), "Redstone Receiver");
	}
}
