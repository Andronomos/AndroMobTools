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
                .add(ModBlocks.MOB_CLONER.get())
                .add(ModBlocks.ITEM_ATTRACTOR.get())
                .add(ModBlocks.ITEM_INCINERATOR.get())
                .add(ModBlocks.MOB_KILLING_PAD.get())
                .add(ModBlocks.WEAK_ACCELERATION_PAD.get())
                .add(ModBlocks.STRONG_ACCELERATION_PAD.get())
                .add(ModBlocks.ITEM_ATTRACTOR.get())
                .add(ModBlocks.FARMER_UNIT.get())
                .add(ModBlocks.WIRELESS_LIGHT.get())
                .add(ModBlocks.REDSTONE_RECEIVER.get())
                .add(ModBlocks.REDSTONE_TRANSMITTER.get());

        //tag(BlockTags.NEEDS_DIAMOND_TOOL)
        //        .add(ModBlocks.ADVANCED_SPAWNER.get());
    }

    @Override
    public String getName() {
        return "Mob Tools Tags";
    }
}
