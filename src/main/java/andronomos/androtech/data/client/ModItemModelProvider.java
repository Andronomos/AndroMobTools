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
		basicItem("sharpness_augment");
		basicItem("looting_augment");
		basicItem("fire_augment");
		basicItem("fake_sword");
		basicItem("nanite_enhanced_pickaxe");
		basicItem("nanite_enhanced_axe");
		basicItem("nanite_enhanced_shovel");
		basicItem("nanite_enhanced_sword");
		basicItem("basic_chip");
		basicItem("advanced_chip");
		basicItem("elite_chip");
		basicItem("repulsor_width_upgrade");
		basicItem("repulsor_height_upgrade");
		basicItem("repulsor_distance_upgrade");

		multiStateItem("gps_recorder", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		multiStateItem("portable_entity_vacuum", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		multiStateItem("mob_storage_device", "_activated", PropertyOverrideRegistry.IS_ACTIVATED);
		multiStateItem("fluid_evaporator", "_lava", PropertyOverrideRegistry.MODE_LAVA);
	}

	private ItemModelBuilder basicItem(String name) {
		return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
	}

	private void multiStateItem(String name, String textureKey, ResourceLocation resource) {
		ItemModelBuilder modelNormal = basicItem(name);
		ModelFile modelActivated = basicItem(name + textureKey);
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
