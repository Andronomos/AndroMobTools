package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModPropertyOverrides;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AndroTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        createSingleTexture("basic_chip");
        createSingleTexture("advanced_chip");
        createSingleTexture("nanite_enhanced_pickaxe");
        createSingleTexture("nanite_enhanced_axe");
        createSingleTexture("nanite_enhanced_shovel");
        createSingleTexture("nanite_enhanced_sword");
        buildActivatableItem("mob_cloning_module");
        buildActivatableItem("portable_item_attractor");
        buildActivatableItem("portable_item_mender");
        buildActivatableItem("block_gps_recorder");
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
