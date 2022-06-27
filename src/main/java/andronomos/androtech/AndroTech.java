package andronomos.androtech;

import andronomos.androtech.block.itemdeconstructor.ItemDeconstructorScreen;
import andronomos.androtech.block.itemincinerator.ItemIncineratorScreen;
import andronomos.androtech.block.amethystharvester.AmethystHarvesterScreen;
import andronomos.androtech.block.animalfarmer.AnimalFarmerScreen;
import andronomos.androtech.block.blockminer.BlockMinerScreen;
import andronomos.androtech.block.cropfarmer.CropFarmerScreen;
import andronomos.androtech.block.itemattractor.ItemAttractorScreen;
import andronomos.androtech.block.itemmender.ItemMenderScreen;
import andronomos.androtech.block.mobcloner.MobClonerScreen;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadScreen;
import andronomos.androtech.block.redstonetransmitter.RedstoneTransmitterScreen;
import andronomos.androtech.event.SpawnerEventHandler;
import andronomos.androtech.event.TeleportInhibitorEvent;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.registry.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		AndroTechPacketHandler.register();
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModContainers.CONTAINERS.register(modEventBus);
		ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
		//ModLoot.LOOT_MODIFIERS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SpawnerEventHandler());
		MinecraftForge.EVENT_BUS.register(new TeleportInhibitorEvent());

		modEventBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ModContainers.MOB_CLONER.get(), MobClonerScreen::new);
			MenuScreens.register(ModContainers.ITEM_ATTRACTOR.get(), ItemAttractorScreen::new);
			MenuScreens.register(ModContainers.ITEM_INCINERATOR.get(), ItemIncineratorScreen::new);
			MenuScreens.register(ModContainers.CROP_FARMER.get(), CropFarmerScreen::new);
			MenuScreens.register(ModContainers.ANIMAL_FARMER.get(), AnimalFarmerScreen::new);
			MenuScreens.register(ModContainers.ITEM_MENDER.get(), ItemMenderScreen::new);
			MenuScreens.register(ModContainers.MOB_KILLING_PAD.get(), MobKillingPadScreen::new);
			MenuScreens.register(ModContainers.REDSTONE_TRANSMITTER.get(), RedstoneTransmitterScreen::new);
			MenuScreens.register(ModContainers.AMETHYST_HARVESTER.get(), AmethystHarvesterScreen::new);
			MenuScreens.register(ModContainers.BLOCK_MINER.get(), BlockMinerScreen::new);
			MenuScreens.register(ModContainers.ITEM_DECONSTRUCTOR.get(), ItemDeconstructorScreen::new);

			ModPropertyOverrides.register();
		});

		ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOB_CLONER.get(), RenderType.cutout());
	}
}
