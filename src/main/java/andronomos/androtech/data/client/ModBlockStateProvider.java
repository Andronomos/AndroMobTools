package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AndroTech.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
            String blockName = ForgeRegistries.BLOCKS.getKey(b).getPath();



        });
    }


    private void registerSimpleStateAndModel(Block block, String name) {
        simpleBlock(block);
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), modLoc("block/" + name));
    }
}