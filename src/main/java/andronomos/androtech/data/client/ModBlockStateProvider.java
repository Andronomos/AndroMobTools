package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.machine.ATMachineBlock;
import andronomos.androtech.block.pad.PadBlock;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.RotatablePadBlock;
import andronomos.androtech.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
		BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
			String blockName = ForgeRegistries.BLOCKS.getKey(b).getPath();

			if(b instanceof PadBlock) {
				registerPadStateAndModel(b, blockName + "_top");
			} else if (b instanceof ATMachineBlock machineBlock) {
				registerMachineBlockState(machineBlock);
			} else {
				registerBlockStateAndModel(b, blockName, blockName);
			}
		});
	}

	private void registerMachineBlockState(ATMachineBlock machine) {
		String machineName = ForgeRegistries.BLOCKS.getKey(machine).getPath();

		if (machine.hasMultipleStates) {
			getVariantBuilder(machine).forAllStates(state -> {
				boolean isPowered = state.getValue(BlockStateProperties.POWERED);

				String stateTextureKey = "_off";
				String topTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("top_off") ? "top_off" : "top"));
				String bottomTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("bottom_off") ? "bottom_off" : "bottom"));
				String sideTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("side_off") ? "side_off" : "side"));

				if (isPowered) {
					stateTextureKey = "_on";
					topTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("top_on") ? "top_on" : "top"));
					bottomTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("bottom_on") ? "bottom_on" : "bottom"));
					sideTexture = String.format("block/%s", machine.textures.get(machine.textures.containsKey("side_on") ? "side_on" : "side"));
				}

				ResourceLocation sideResource = modLoc(sideTexture);
				ModelFile model = models().cube(machineName + stateTextureKey,
								modLoc(bottomTexture), modLoc(topTexture),
								sideResource, sideResource, sideResource, sideResource)
						.texture("particle", sideResource);

				return ConfiguredModel.builder()
						.modelFile(model)
						.build();
			});

			itemModels().withExistingParent(machineName, modLoc("block/" + machineName + "_off"));
		} else {
			String topTexture = String.format("block/%s", machine.textures.get("top"));
			String bottomTexture = String.format("block/%s", machine.textures.get("bottom"));
			String sideTexture = String.format("block/%s", machine.textures.get("side"));
			String frontTexture = String.format("block/%s", machine.textures.get("front"));

			ModelFile model = models().cube(ForgeRegistries.BLOCKS.getKey(machine).getPath(),
					modLoc(bottomTexture),
					modLoc(topTexture),
					modLoc(frontTexture),
					modLoc(sideTexture),
					modLoc(sideTexture),
					modLoc(sideTexture))
					.texture("particle", sideTexture);

			simpleBlock(machine, model);
			itemModels().withExistingParent(machineName, modLoc("block/" + machineName));
		}
	}



	private void registerPadStateAndModel(Block block, String top) {
		String blockName = ForgeRegistries.BLOCKS.getKey(block).getPath();

		ModelFile model = models().withExistingParent(blockName, modLoc("pad_base"))
				.texture("particle", modLoc("block/" + top))
				.texture("design", modLoc("block/" + top))
				.texture("pad", modLoc("block/machine_bottom"));

		if(block instanceof RotatablePadBlock) {
			getVariantBuilder(block).forAllStatesExcept(state -> {
				Direction direction = state.getValue(PadEffectBlock.FACING);

				int yRot = switch (direction) {
					case EAST -> 90;
					case WEST -> 270;
					case SOUTH -> 180;
					default -> 0;
				};

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
