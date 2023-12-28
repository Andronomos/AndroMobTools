package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.RotatablePadBlock;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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
				case "PadBlock" -> registerPadStateAndModel(b, blockName + "_top", b instanceof RotatablePadBlock);
				default -> registerBlockStateAndModel(b, blockName, textureFolder);
			}
		});
	}

	private void registerPadStateAndModel(Block block, String top, boolean isDirectional) {
		String blockName = ForgeRegistries.BLOCKS.getKey(block).getPath();

		ModelFile model = models().withExistingParent(blockName, modLoc("pad_base"))
				.texture("particle", modLoc("block/" + top))
				.texture("design", modLoc("block/" + top))
				.texture("pad", modLoc("block/machine_bottom"));

		if(isDirectional) {
			getVariantBuilder(block).forAllStatesExcept(state -> {
				Direction direction = state.getValue(PadEffectBlock.FACING);

				int yRot = 0;

				switch (direction) {
					case EAST:
						yRot = 90;
						break;
					case WEST:
						yRot = 270;
						break;
					case SOUTH:
						yRot = 180;
						break;
				}

				return ConfiguredModel.builder()
						.modelFile(model)
						.rotationY(yRot)
						.build();
			});
		} else {
			simpleBlock(block, model);
		}

		itemModels().withExistingParent(blockName, modLoc("block/" + blockName));
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
