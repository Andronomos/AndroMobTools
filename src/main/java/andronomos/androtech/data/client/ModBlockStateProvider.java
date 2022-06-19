package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.AndroTechMachine;
import andronomos.androtech.block.pad.PadBlock;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.RotatablePadBlock;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AndroTech.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
            String blockName = b.getRegistryName().getPath();

            if(b instanceof AndroTechMachine machine) {
                if(machine.hasMultipleStates) {
                    registerMultiStateMachine(machine);
                } else {
                    registerSingleStateMachine(machine);
                }
            }

            if(b instanceof PadBlock) {
                registerPadStateAndModel(b, blockName + "_top", b instanceof RotatablePadBlock);
            }
        });
    }


    private void registerSingleStateMachine(AndroTechMachine machine) {
        String machineName = machine.getRegistryName().getPath();
        String topTexture = machine.useDefaultTopTexture ? "block/machine_top" : String.format("block/%s_top", machineName);
        String bottomTexture = machine.useDefaultBottomTexture ? "block/machine_bottom" : String.format("block/%s_bottom", machineName);
        String sideTexture = String.format("block/%s_side", machineName);
        String frontTexture = machine.isDirectional ? String.format("block/%s_front", machineName) : sideTexture;

        ModelFile model = models().cube(machine.getRegistryName().getPath(),
                modLoc(bottomTexture),
                modLoc(topTexture),
                modLoc(frontTexture),
                modLoc(sideTexture),
                modLoc(sideTexture),
                modLoc(sideTexture));

        if(machine.isDirectional) {
            getVariantBuilder(machine).forAllStatesExcept(state -> {
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
            simpleBlock(machine, model);
        }

        itemModels().withExistingParent(machineName, modLoc("block/" + machineName));
    }

    private void registerMultiStateMachine(AndroTechMachine machine) {
        String machineName = machine.getRegistryName().getPath();

        getVariantBuilder(machine).forAllStates(state -> {
            boolean isPowered = state.getValue(BlockStateProperties.POWERED);

            String topTexture = "block/";
            String bottomTexture = "block/";

            if(!machine.useDefaultBottomTexture) {
                topTexture += machineName;
                topTexture += isPowered ? "_on_top" : "_off_top";
            } else {
                topTexture += "machine_top";
            }

            if(!machine.useDefaultBottomTexture) {
                bottomTexture += machineName;
                bottomTexture += isPowered ? "_on_bottom" : "_off_bottom";
            } else {
                bottomTexture += "machine_bottom";
            }

            ResourceLocation side = modLoc("block/" + machineName + "_off_side");
            ModelFile model = models().cube(machineName + "_off", modLoc(bottomTexture), modLoc(topTexture), side, side, side, side);

            if(isPowered) {
                side = modLoc("block/" + machineName + "_on_side");
                model = models().cube(machineName + "_on", modLoc(bottomTexture), modLoc(topTexture), side, side, side, side);
            }

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        itemModels().withExistingParent(machine.getRegistryName().getPath(), modLoc("block/" + machineName + "_off"));
    }

    private void registerPadStateAndModel(Block block, String top, boolean isDirectional) {
        ModelFile model = models().withExistingParent(block.getRegistryName().getPath(), modLoc("pad_base"))
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

        itemModels().withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
    }

    private void registerSimpleStateAndModel(Block block, String name) {
        simpleBlock(block);
        itemModels().withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + name));
    }
}