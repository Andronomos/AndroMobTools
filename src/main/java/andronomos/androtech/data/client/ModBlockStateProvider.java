package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, AndroTech.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String blockName = ForgeRegistries.BLOCKS.getKey(b).getPath();
			String blockType = b.getClass().getSimpleName();
			String textureFolder = blockName;

			switch (blockType) {
				default -> registerBlockStateAndModel(b, blockName, textureFolder);
			}
		});
	}

	private void registerBlockStateAndModel(Block block, String name, String textureFolder) {
		ModelFile model = !textureFolder.equals("") ? models().cubeAll(name, modLoc("block/" + textureFolder + "/" + name))
				: models().cubeAll(name, modLoc("block/" + name));
		simpleBlock(block, model);
		registerItemModel(name);
	}

	private void registerItemModel(String name) {
		itemModels().withExistingParent(name, modLoc("block/" + name));
	}
}
