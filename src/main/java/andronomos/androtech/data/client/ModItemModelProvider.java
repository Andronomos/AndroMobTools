package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, AndroTech.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		createSingleTexture("damage_pad_upgrade_sharpness");
		createSingleTexture("damage_pad_upgrade_looting");
		createSingleTexture("damage_pad_upgrade_fire");
		createSingleTexture("damage_pad_upgrade_smite");
		createSingleTexture("damage_pad_upgrade_arthro");
	}

	private ItemModelBuilder createSingleTexture(String name) {
		return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
	}
}
