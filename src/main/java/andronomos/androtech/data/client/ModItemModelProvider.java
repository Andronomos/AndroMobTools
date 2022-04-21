package andronomos.androtech.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, andronomos.androtech.AndroTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated, "mob_storage_cell");
        builder(itemGenerated, "basic_chip");
        builder(itemGenerated, "advanced_chip");
        builder(itemGenerated, "portable_loot_attractor");
        builder(itemGenerated, "debug_stick");
        builder(itemGenerated, "gps_card");
        builder(itemGenerated, "speed_emitter");
        builder(itemGenerated, "fire_resistance_emitter");
        builder(itemGenerated, "poison_nullifier");
        builder(itemGenerated, "wither_nullifier");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
