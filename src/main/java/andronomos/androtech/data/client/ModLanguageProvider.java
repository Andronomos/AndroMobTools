package andronomos.androtech.data.client;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static andronomos.androtech.AndroTech.TAB_NAME;
import static andronomos.androtech.block.MobClonerBlock.SCREEN_MOB_CLONER;
import static andronomos.androtech.block.LootAttractorBlock.SCREEN_LOOT_ATTRACTOR;
import static andronomos.androtech.block.LootIncineratorBlock.SCREEN_LOOT_INCINERATOR;
import static andronomos.androtech.block.pad.MobKillingPadBlock.SCREEN_MOB_KILLING_PAD;
import static andronomos.androtech.item.MobStorageCellItem.*;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen, String locale) {
		super(gen, andronomos.androtech.AndroTech.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + TAB_NAME, "Andro Mob Utils");

		//items
		add(ModItems.MOB_STORAGE_CELL.get(), "Mob Storage Cell");
		add(TOOLTIP_MOB_STORAGE_CELL_MOB, "Mob: ");
		add(TOOLTIP_MOB_STORAGE_CELL_HEALTH, "Health: ");
		add(ModItems.PORTABLE_LOOT_ATTRACTOR.get(), "Portable Loot Attractor");
		add(ModItems.BASIC_CHIP.get(), "Basic Chip");
		add(ModItems.ADVANCED_CHIP.get(), "Advanced Chip");

		//blocks
		add(SCREEN_MOB_CLONER, "Mob Cloner");
		add(ModBlocks.MOB_CLONER.get(), "Mob Cloner");
		add(ModBlocks.LOOT_ATTRACTOR.get(), "Loot Attractor");
		add(SCREEN_LOOT_ATTRACTOR, "Loot Attractor");
		add(ModBlocks.LOOT_INCINERATOR.get(), "Loot Incinerator");
		add(SCREEN_LOOT_INCINERATOR, "Loot Incinerator");
		add(ModBlocks.MOB_KILLING_PAD.get(), "Mob Killing Pad");
		add(SCREEN_MOB_KILLING_PAD, "Mob Killing Pad");
		add(ModBlocks.WEAK_ACCELERATION_PAD.get(), "Weak Acceleration Pad");
		add(ModBlocks.STRONG_ACCELERATION_PAD.get(), "Strong Acceleration Pad");
	}
}
