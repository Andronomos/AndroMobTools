package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, AndroTech.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

	}
}
