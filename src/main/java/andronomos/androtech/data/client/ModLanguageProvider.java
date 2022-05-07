package andronomos.androtech.data.client;

import andronomos.androtech.block.machine.ItemIncinerator;
import andronomos.androtech.block.machine.RedstoneTransmitterBlock;
import andronomos.androtech.block.machine.CropFarmer;
import andronomos.androtech.block.machine.ItemAttractor;
import andronomos.androtech.block.machine.MobCloner;
import andronomos.androtech.block.pad.MobKillingPadBlock;
import andronomos.androtech.item.BlockGpsModule;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

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

		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");

		add(ModItems.MOB_CLONING_MODULE.get(), "Mob Cloning Module");
		add(TOOLTIP_MOB_CLONING_MODULE_MOB, "Mob: ");
		add(TOOLTIP_MOB_CLONING_MODULE_HEALTH, "Health: ");
		add(ModItems.BLOCK_GPS_MODULE.get(), "Block GPS Module");
		add(BlockGpsModule.TOOLTIP_BLOCK_GPS_MODULE, "Location");
		add(BlockGpsModule.TOOLTIP_BLOCK_GPS_MODULE_X, "X: ");
		add(BlockGpsModule.TOOLTIP_BLOCK_GPS_MODULE_Y, "Y: ");
		add(BlockGpsModule.TOOLTIP_BLOCK_GPS_MODULE_Z, "Z: ");
		add(BlockGpsModule.BLOCK_GPS_MODULE_SAVED, "Saved Block Position ");
		add(ModItems.ITEM_ATTRACTOR_MODULE.get(), "Item Attractor Module");
		add(ModItems.ITEM_REPAIR_MODULE.get(), "Item Repair Module");
		add(ModItems.NANITE_ENHANCED_PICKAXE.get(), "Nanite Enhanced Pickaxe");
		add(ModItems.NANITE_ENHANCED_AXE.get(), "Nanite Enhanced Axe");
		add(ModItems.NANITE_ENHANCED_SHOVEL.get(), "Nanite Enhanced Shovel");
		add(ModItems.NIGHT_VISION_EMITTER.get(), "Night Vision Emitter");
		add(ModItems.SWIFTNESS_EMITTER.get(), "Swiftness Emitter");
		add(ModItems.FIRE_RESISTANCE_EMITTER.get(), "Fire Resistance Emitter");
		add(ModItems.WATER_BREATHING_EMITTER.get(), "Water Breathing Emitter");
		add(ModItems.REGENERATION_EMITTER.get(), "Regeneration Emitter");
		add(ModItems.POISON_NULLIFIER.get(), "Poison Nullifier");
		add(ModItems.WITHER_NULLIFIER.get(), "Wither Nullifier");


		/** Blocks **/
		add(ModBlocks.CROP_FARMER.get(), "Crop Farmer");
		add(CropFarmer.SCREEN_CROP_FARMER, "Crop Farmer");

		add(ModBlocks.ITEM_INCINERATOR.get(), "Item Incinerator");
		add(ItemIncinerator.SCREEN_ITEM_INCINERATOR, "Item Incinerator");

		add(ModBlocks.ITEM_ATTRACTOR.get(), "Item Attractor");
		add(ItemAttractor.SCREEN_ITEM_ATTRACTOR, "Item Attractor");

		add(ModBlocks.MOB_CLONER.get(), "Mob Cloner");
		add(MobCloner.SCREEN_MOB_CLONER, "Mob Cloner");

		add(ModBlocks.REDSTONE_TRANSMITTER.get(), "Redstone Transmitter");
		add(RedstoneTransmitterBlock.SCREEN_REDSTONE_TRANSMITTER, "Redstone Transmitter");

		add(ModBlocks.REDSTONE_RECEIVER.get(), "Redstone Receiver");

		add(ModBlocks.MOB_KILLING_PAD.get(), "Mob Killing Pad");
		add(MobKillingPadBlock.SCREEN_MOB_KILLING_PAD, "Mob Killing Pad");

		add(ModBlocks.WEAK_ACCELERATION_PAD.get(), "Weak Acceleration Pad");
		add(ModBlocks.STRONG_ACCELERATION_PAD.get(), "Strong Acceleration Pad");
	}
}
