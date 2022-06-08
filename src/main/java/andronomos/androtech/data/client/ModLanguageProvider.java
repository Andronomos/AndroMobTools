package andronomos.androtech.data.client;

import andronomos.androtech.block.machine.ItemIncinerator.ItemIncinerator;
import andronomos.androtech.block.machine.cropfarmer.CropFarmer;
import andronomos.androtech.block.machine.itemattractor.ItemAttractor;
import andronomos.androtech.block.machine.itemmender.ItemMender;
import andronomos.androtech.block.machine.pad.mobkillingpad.MobKillingPadBlock;
import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitter;
import andronomos.androtech.item.BlockGpsRecorder;
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

		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");
		add(ModItems.MOB_CLONING_MODULE.get(), "Mob Cloning Module");
		add(TOOLTIP_MOB_CLONING_MODULE_MOB, "Mob: ");
		add(TOOLTIP_MOB_CLONING_MODULE_HEALTH, "Health: ");
		add(ItemAttractor.SCREEN_ITEM_ATTRACTOR, "Item Attractor");
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
		add(ItemIncinerator.SCREEN_ITEM_INCINERATOR, "Item Incinerator");
		add(CropFarmer.SCREEN_CROP_FARMER, "Crop Farmer");
		add(ItemMender.SCREEN_ITEM_MENDER, "Item Mender");
		add(MobKillingPadBlock.SCREEN_MOB_KILLING_PAD, "Mob Killing Pad");
		add(ModItems.PORTABLE_ITEM_ATTRACTOR.get(), "Portable Item Attractor");
		add(ModItems.PORTABLE_ITEM_MENDER.get(), "Portable Item Mender");
		add(RedstoneTransmitter.SCREEN_REDSTONE_TRANSMITTER, "Redstone Transmitter");
		add("gui.androtech.powered_on", "Powered On");
		add("gui.androtech.powered_off", "Powered Off");
	}
}
