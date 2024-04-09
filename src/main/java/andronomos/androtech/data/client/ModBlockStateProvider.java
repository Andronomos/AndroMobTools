package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, AndroTech.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

	}


}
