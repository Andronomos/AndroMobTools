package andronomos.androtech.data;

import andronomos.androtech.data.client.ModBlockStateProvider;
import andronomos.androtech.data.client.ModItemModelProvider;
import andronomos.androtech.data.client.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if(event.includeServer()) {
            generator.addProvider(true, new ModRecipeProvider(generator));
            generator.addProvider(true, new ModLootTableProvider(generator));
            generator.addProvider(true, new ModBlockTagsProvider(generator, event.getExistingFileHelper()));
        }

        if(event.includeClient()) {
            generator.addProvider(true, new ModBlockStateProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(true, new ModItemModelProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(true, new ModLanguageProvider(generator, "en_us"));
        }
    }
}
