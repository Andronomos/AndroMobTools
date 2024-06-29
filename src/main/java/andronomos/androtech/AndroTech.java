package andronomos.androtech;

import andronomos.androtech.block.entityvacuum.EntityVacuumScreen;
import andronomos.androtech.block.mobrepulsor.MobRepulsorScreen;
import andronomos.androtech.block.mobkiller.MobKillerScreen;
import andronomos.androtech.block.itemincinerator.ItemIncineratorScreen;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterScreen;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.event.TeleportInhibitorEvent;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.registry.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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

@Mod(AndroTech.MODID)
public class AndroTech {
	public static final String MODID = "androtech";
	public static final Logger LOGGER = LogManager.getLogger(andronomos.androtech.AndroTech.MODID);
	public static final GameProfile PROFILE = new GameProfile(UUID.randomUUID(), "[AndroTech]");

	public AndroTech() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AndroTechConfig.CONFIG, "androtech.toml");
		AndroTechConfig.loadConfig(FMLPaths.CONFIGDIR.get().resolve("androtech.toml"));
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		AndroTechPacketHandler.register();
		BlockRegistry.BLOCKS.register(modEventBus);
		ItemRegistry.ITEMS.register(modEventBus);
		CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
		BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
		MenuTypeRegistry.MENU_TYPES.register(modEventBus);
		FluidRegistry.FLUIDS.register(modEventBus);
		FluidTypeRegistry.FLUID_TYPES.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new TeleportInhibitorEvent());
		modEventBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MenuTypeRegistry.MOB_KILLER_MENU.get(), MobKillerScreen::new);
			MenuScreens.register(MenuTypeRegistry.ENTITY_VACUUM_MENU.get(), EntityVacuumScreen::new);
			MenuScreens.register(MenuTypeRegistry.ITEM_INCINERATOR_MENU.get(), ItemIncineratorScreen::new);
			MenuScreens.register(MenuTypeRegistry.REDSTONE_SIGNAL_TRANSMITTER_MENU.get(), RedstoneSignalTransmitterScreen::new);
			MenuScreens.register(MenuTypeRegistry.MOB_REPULSOR_MENU.get(), MobRepulsorScreen::new);
			ItemBlockRenderTypes.setRenderLayer(FluidRegistry.LIQUID_XP.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FluidRegistry.LIQUID_XP_FLOWING.get(), RenderType.translucent());
			PropertyOverrideRegistry.register();
		});
	}
}
