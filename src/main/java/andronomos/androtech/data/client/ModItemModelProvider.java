package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.PropertyOverrideRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, AndroTech.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		createSingleTexture("sharpness_augment");
		createSingleTexture("looting_augment");
		createSingleTexture("fire_augment");
		//createSingleTexture("smite_augment");
		createSingleTexture("fake_sword");
		createSingleTexture("nanite_enhanced_pickaxe");
		createSingleTexture("nanite_enhanced_axe");
		createSingleTexture("nanite_enhanced_shovel");
		createSingleTexture("nanite_enhanced_sword");
		createSingleTexture("basic_chip");
		createSingleTexture("advanced_chip");
		createSingleTexture("elite_chip");

		buildMultiStateItemModel("gps_recorder");
		buildMultiStateItemModel("portable_item_attractor");
	}

	private ItemModelBuilder createSingleTexture(String name) {
		return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
	}

	private void buildMultiStateItemModel(String name) {
		ItemModelBuilder modelNormal = createSingleTexture(name);
		ModelFile modelactivated = createSingleTexture(name + "_activated");
		modelNormal.override()
				.predicate(PropertyOverrideRegistry.IS_ACTIVATED, 0)
				.model(modelNormal)
				.end()
				.override()
				.predicate(PropertyOverrideRegistry.IS_ACTIVATED, 1)
				.model(modelactivated)
				.end();
	}
}
