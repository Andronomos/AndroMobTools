package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AndroTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        createSingleTexture("basic_chip");
        createSingleTexture("advanced_chip");
    }

    private ItemModelBuilder createSingleTexture(String name) {
        return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
    }
}
