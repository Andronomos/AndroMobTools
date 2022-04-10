package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, andronomos.androtech.AndroTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TEST_BLOCK.get())
                .add(ModBlocks.MOB_CLONER.get())
                .add(ModBlocks.LOOT_ATTRACTOR.get())
                .add(ModBlocks.LOOT_INCINERATOR.get())
                .add(ModBlocks.MOB_KILLING_PAD.get())
                .add(ModBlocks.WEAK_ACCELERATION_PAD.get())
                .add(ModBlocks.STRONG_ACCELERATION_PAD.get())
                .add(ModBlocks.LOOT_ATTRACTOR.get())
                .add(ModBlocks.CROP_HARVESTER.get());

        //tag(BlockTags.NEEDS_DIAMOND_TOOL)
        //        .add(ModBlocks.ADVANCED_SPAWNER.get());
    }

    @Override
    public String getName() {
        return "Mob Tools Tags";
    }
}
