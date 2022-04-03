package andronomos.andromobutils;

import andronomos.andromobutils.gui.*;
import andronomos.andromobutils.event.SpawnerEventHandler;
import andronomos.andromobutils.network.MobToolsPacketHandler;
import andronomos.andromobutils.registry.ModBlockEntities;
import andronomos.andromobutils.registry.ModBlocks;
import andronomos.andromobutils.registry.ModContainers;
import andronomos.andromobutils.registry.ModItems;
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


@Mod(AndroMobUtils.MOD_ID)
public class AndroMobUtils
{
    public static final String MOD_ID = "andromobutils";
    public static final String TAB_NAME = "andromobutils_group";
    public static final CreativeModeTab ANDROMOBUTILS_TAB = new AndroMobUtilsItemGroup(TAB_NAME);
    public static final Logger LOGGER = LogManager.getLogger(AndroMobUtils.MOD_ID);

    public AndroMobUtils() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MobToolsPacketHandler.register();
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new SpawnerEventHandler());

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.MOB_CLONER.get(), GoldenSpawnerScreen::new);
            MenuScreens.register(ModContainers.LOOT_ATTRACTOR.get(), LootAttractorScreen::new);
            MenuScreens.register(ModContainers.LOOT_INCINERATOR.get(), LootIncineratorScreen::new);
            MenuScreens.register(ModContainers.MOB_KILLING_PAD.get(), MobKillingPadScreen::new);
        });

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOB_CLONER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LOOT_ATTRACTOR.get(), RenderType.cutout());
    }
}
