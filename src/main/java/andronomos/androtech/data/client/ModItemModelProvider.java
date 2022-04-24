package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModPropertyOverrides;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
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

        buildActivatableItem("mob_dna_unit");
        createSingleTexture("basic_chip");
        createSingleTexture("advanced_chip");
        createSingleTexture("withered_bone");
        createSingleTexture("debug_stick");
        createSingleTexture("gps_unit");

        buildActivatableItem("attractor_unit");
        buildActivatableItem("speed_emitter");
        buildActivatableItem("fire_resistance_emitter");
        buildActivatableItem("regeneration_emitter");
        buildActivatableItem("poison_nullifier");
        buildActivatableItem("wither_nullifier");
        buildActivatableItem("nanite_unit");
    }

    private ItemModelBuilder createSingleTexture(String name) {
        return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
    }

    private void buildActivatableItem(String name) {
        ItemModelBuilder modelNormal = createSingleTexture(name);
        ModelFile modelactivated = createSingleTexture(name + "_activated");
        modelNormal.override()
                .predicate(ModPropertyOverrides.IS_ACTIVATED, 0)
                .model(modelNormal)
                .end()
                .override()
                .predicate(ModPropertyOverrides.IS_ACTIVATED, 1)
                .model(modelactivated)
                .end();
    }
}
