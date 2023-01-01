package andronomos.androtech;

import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.registry.*;
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

@Mod(AndroTech.MOD_ID)
public class AndroTech {
	public static final String MOD_ID = "androtech";
	public static final String TAB_NAME = "androtech_group";
	public static final CreativeModeTab ANDROTECH_TAB = new AndroTechItemGroup(TAB_NAME);
	public static final Logger LOGGER = LogManager.getLogger(andronomos.androtech.AndroTech.MOD_ID);

	public AndroTech() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AndroTechConfig.CONFIG, "androtech.toml");
		AndroTechConfig.loadConfig(FMLPaths.CONFIGDIR.get().resolve("androtech.toml"));
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ModPropertyOverrides.register();
		});
	}
}
