package andronomos.androtech.data;

import andronomos.androtech.data.client.ModBlockStateProvider;
import andronomos.androtech.data.client.ModItemModelProvider;
import andronomos.androtech.data.client.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if(event.includeServer()) {
            generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new ModLootTableProvider(generator));
            generator.addProvider(new ModBlockTagsProvider(generator, event.getExistingFileHelper()));
        }

        if(event.includeClient()) {
            generator.addProvider(new ModBlockStateProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(new ModItemModelProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(new ModLanguageProvider(generator, "en_us"));
        }
    }
}
