package andronomos.androtech;

import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.CreativeTabRegistry;
import andronomos.androtech.registry.ItemRegistry;
import com.mojang.authlib.GameProfile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(AndroTech.MODID)
public class AndroTech {
	public static final String MODID = "androtech";
	public static final Logger LOGGER = LogManager.getLogger(andronomos.androtech.AndroTech.MODID);
	public static final GameProfile PROFILE = new GameProfile(UUID.randomUUID(), "[AndroTech]");

	public AndroTech() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BlockRegistry.BLOCKS.register(modEventBus);
		ItemRegistry.ITEMS.register(modEventBus);
		CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
		BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {

	}
}
