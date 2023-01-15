package andronomos.androtech;

import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGeneratorMenu;
import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGeneratorScreen;
import andronomos.androtech.block.machine.itemmender.ItemMenderScreen;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerScreen;
import andronomos.androtech.block.machine.mobcloner.MobClonerScreen;
import andronomos.androtech.block.machine.itemattractor.ItemAttractorScreen;
import andronomos.androtech.block.machine.itemincinerator.ItemIncineratorScreen;
import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitterScreen;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadScreen;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.event.SpawnerEventHandler;
import andronomos.androtech.event.TeleportInhibitorEvent;
import andronomos.androtech.event.easyharvest.HarvestEventHandler;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.registry.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(AndroTech.MOD_ID)
public class AndroTech {
	public static final String MOD_ID = "androtech";
	public static final String TAB_NAME = "androtech_group";
	public static final CreativeModeTab ANDROTECH_TAB = new AndroTechItemGroup(TAB_NAME);
	public static final Logger LOGGER = LogManager.getLogger(andronomos.androtech.AndroTech.MOD_ID);
	public static final GameProfile PROFILE = new GameProfile(UUID.randomUUID(), "[AndroTech]");

	public AndroTech() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AndroTechConfig.CONFIG, "androtech.toml");
		AndroTechConfig.loadConfig(FMLPaths.CONFIGDIR.get().resolve("androtech.toml"));
		AndroTechPacketHandler.register();
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModMenuTypes.MENU_TYPES.register(modEventBus);
		ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SpawnerEventHandler());
		MinecraftForge.EVENT_BUS.register(new TeleportInhibitorEvent());
		//MinecraftForge.EVENT_BUS.register(new HarvestEventHandler());
		modEventBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ModMenuTypes.CROP_FARMER.get(), CropFarmerScreen::new);
			MenuScreens.register(ModMenuTypes.MOB_CLONER.get(), MobClonerScreen::new);
			MenuScreens.register(ModMenuTypes.ITEM_ATTRACTOR.get(), ItemAttractorScreen::new);
			MenuScreens.register(ModMenuTypes.ITEM_INCINERATOR.get(), ItemIncineratorScreen::new);
			MenuScreens.register(ModMenuTypes.REDSTONE_TRANSMITTER.get(), RedstoneTransmitterScreen::new);
			MenuScreens.register(ModMenuTypes.MOB_KILLING_PAD.get(), MobKillingPadScreen::new);
			MenuScreens.register(ModMenuTypes.ITEM_MENDER.get(), ItemMenderScreen::new);
			MenuScreens.register(ModMenuTypes.CREATIVE_ENERGY_GENERATOR.get(), CreativeEnergyGeneratorScreen::new);

			ModPropertyOverrides.register();
		});
	}
}
