package andronomos.andromobutils.data.client;

import andronomos.andromobutils.AndroMobUtils;
import andronomos.andromobutils.block.pad.emiiter.EmitterPadBlock;
import andronomos.andromobutils.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AndroMobUtils.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachineStateAndModel(ModBlocks.MOB_CLONER.get(), "mob_cloner_top", "machine_bottom", "mob_cloner_side");
        registerMachineStateAndModel(ModBlocks.LOOT_INCINERATOR.get(), "loot_incinerator_top", "machine_bottom", "loot_incinerator_side");
        registerPadStateAndModel(ModBlocks.MOB_KILLING_PAD.get(), "mob_killing_pad_top");
        registerPadStateAndModel(ModBlocks.WEAK_ACCELERATION_PAD.get(), "acceleration_pad_weak_top", true);
        registerPadStateAndModel(ModBlocks.STRONG_ACCELERATION_PAD.get(), "acceleration_pad_strong_top", true);
    }

    private void registerSimpleStateAndModel(Block block, String name) {
        simpleBlock(block);
        itemModels().withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + name));
    }

    private void registerMachineStateAndModel(Block block, String top, String down, String side) {
        ModelFile model = models().cube(block.getRegistryName().getPath(),
                modLoc("block/" + down),
                modLoc("block/" + top),
                modLoc("block/" + side),
                modLoc("block/" + side),
                modLoc("block/" + side),
                modLoc("block/" + side));
        simpleBlock(block, model);
        itemModels().withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
    }

    //private void registerPadStateAndModel(Block block, String top) {
    //    ModelFile model = models().withExistingParent(block.getRegistryName().getPath(), modLoc("pad_base"))
    //            .texture("particle", modLoc("block/" + top))
    //            .texture("design", modLoc("block/" + top))
    //            .texture("pad", modLoc("block/machine_bottom"));
    //    simpleBlock(block, model);
    //    itemModels().withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
    //}

    private void registerPadStateAndModel(Block block, String top) {
        registerPadStateAndModel(block, top, false);
    }

    private void registerPadStateAndModel(Block block, String top, boolean isDirectional) {
        ModelFile model = models().withExistingParent(block.getRegistryName().getPath(), modLoc("pad_base"))
                .texture("particle", modLoc("block/" + top))
                .texture("design", modLoc("block/" + top))
                .texture("pad", modLoc("block/machine_bottom"));

        if(isDirectional) {
            getVariantBuilder(block).forAllStatesExcept(state -> {
                Direction direction = state.getValue(EmitterPadBlock.FACING);

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
}