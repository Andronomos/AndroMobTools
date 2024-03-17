package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.PropertyOverrideRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
		//createSingleTexture("fluid_evaporator");

		buildMultiStateItemModel("gps_recorder", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		buildMultiStateItemModel("item_attraction_emitter", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		buildMultiStateItemModel("mob_storage_device", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		buildMultiStateItemModel("fluid_evaporator", "_lava", PropertyOverrideRegistry.MODE_LAVA);
	}

	private ItemModelBuilder createSingleTexture(String name) {
		return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
	}

	private void buildMultiStateItemModel(String name, String textureKey, ResourceLocation resource) {
		ItemModelBuilder modelNormal = createSingleTexture(name);
		ModelFile modelActivated = createSingleTexture(name + textureKey);
		modelNormal.override()
				.predicate(resource, 0)
				.model(modelNormal)
				.end()
				.override()
				.predicate(resource, 1)
				.model(modelActivated)
				.end();
	}
}
