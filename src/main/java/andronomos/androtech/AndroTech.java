package andronomos.androtech;

import andronomos.androtech.gui.*;
import andronomos.androtech.event.SpawnerEventHandler;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.registry.*;
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


@Mod(AndroTech.MOD_ID)
public class AndroTech
{
    public static final String MOD_ID = "androtech";
    public static final String TAB_NAME = "androtech_group";
    public static final CreativeModeTab ANDROTECH_TAB = new AndroTechItemGroup(TAB_NAME);
    public static final Logger LOGGER = LogManager.getLogger(andronomos.androtech.AndroTech.MOD_ID);

    public AndroTech() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //AndroTechPacketHandler.register();
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        //ModLoot.LOOT_MODIFIERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new SpawnerEventHandler());

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.MOB_CLONER.get(), MobClonerScreen::new);
            MenuScreens.register(ModContainers.ITEM_ATTRACTOR.get(), LootAttractorScreen::new);
            MenuScreens.register(ModContainers.ITEM_INCINERATOR.get(), LootIncineratorScreen::new);
            MenuScreens.register(ModContainers.MOB_KILLING_PAD.get(), MobKillingPadScreen::new);
            MenuScreens.register(ModContainers.CROP_HARVESTER.get(), CropHarvesterScreen::new);
            MenuScreens.register(ModContainers.REDSTONE_TRANSMITTER.get(), RedstoneTransmitterScreen::new);

            ModPropertyOverrides.register();
        });

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOB_CLONER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ITEM_ATTRACTOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WIRELESS_LIGHT.get(), RenderType.cutout());
    }



}
