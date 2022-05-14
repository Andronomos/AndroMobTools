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
        buildActivatableItem("mob_cloning_module");
        buildActivatableItem("item_attractor_module");
        buildActivatableItem("block_gps_module");
        buildActivatableItem("item_repair_module");
        createSingleTexture("basic_chip");
        createSingleTexture("advanced_chip");
        createSingleTexture("debug_stick");
        createSingleTexture("ore_translocator");
        createSingleTexture("nanite_enhanced_pickaxe");
        createSingleTexture("nanite_enhanced_axe");
        createSingleTexture("nanite_enhanced_shovel");
        createSingleTexture("nanite_enhanced_sword");
        buildActivatableItem("night_vision_emitter");
        buildActivatableItem("water_breathing_emitter");
        buildActivatableItem("swiftness_emitter");
        buildActivatableItem("fire_resistance_emitter");
        buildActivatableItem("regeneration_emitter");
        buildActivatableItem("poison_nullifier");
        buildActivatableItem("wither_nullifier");
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
