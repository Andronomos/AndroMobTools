package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, andronomos.androtech.AndroTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(b);

            if(b instanceof WallBlock wall) {
                tag(BlockTags.WALLS).add(wall);
            }

            if(b instanceof FenceBlock fence) {
                tag(BlockTags.FENCES).add(fence);
            }
        });
    }

    @Override
    public String getName() {
        return "AndroTech Tags";
    }
}
