package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        registerChip(ModItems.BASIC_CHIP.get(), Items.GOLD_INGOT, consumer);
        registerChip(ModItems.ADVANCED_CHIP.get(), Items.DIAMOND, consumer);
        registerModule(ModItems.MOB_CLONING_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.GLASS_BOTTLE, consumer);
        registerModule(ModItems.PORTABLE_ITEM_ATTRACTOR.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, consumer);
        registerModule(ModItems.BLOCK_GPS_RECORDER.get(), ModItems.BASIC_CHIP.get(), Items.MAP, consumer);
        registerModule(ModItems.PORTABLE_ITEM_MENDER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, consumer);
        registerMachine(ModBlocks.MOB_CLONER.get(), ModItems.ADVANCED_CHIP.get(), ModItems.MOB_CLONING_MODULE.get(), Items.SPAWNER, consumer);
        registerMachine(ModBlocks.ITEM_ATTRACTOR.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, ModItems.PORTABLE_ITEM_ATTRACTOR.get(), consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_PICKAXE.get(), Items.NETHERITE_PICKAXE,  consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_AXE.get(), Items.NETHERITE_AXE, consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_SHOVEL.get(), Items.NETHERITE_SHOVEL, consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_SWORD.get(), Items.NETHERITE_SWORD, consumer);
        registerMachine(ModBlocks.ITEM_MENDER.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, ModItems.PORTABLE_ITEM_MENDER.get(), consumer);
        registerMachine(ModBlocks.REDSTONE_RECEIVER.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.OBSERVER, consumer);
        registerMachine(ModBlocks.REDSTONE_TRANSMITTER.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.REPEATER, consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ITEM_INCINERATOR.get())
                .define('1', Items.IRON_INGOT)
                .define('2', Items.GLASS)
                .define('3', Items.LAVA_BUCKET)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CROP_FARMER.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE_BLOCK)
                .define('4', Items.CHEST)
                .define('5', ModItems.PORTABLE_ITEM_ATTRACTOR.get())
                .define('6', Items.WATER_BUCKET)
                .pattern("212")
                .pattern("356")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        registerAdvancedPad(ModBlocks.MOB_KILLING_PAD.get(), Items.IRON_SWORD, consumer);
        registerPad(ModBlocks.WEAK_ACCELERATION_PAD.get(), Items.SUGAR, consumer);
        registerPad(ModBlocks.STRONG_ACCELERATION_PAD.get(), Items.RABBIT_FOOT, consumer);
    }

    private void registerPad(Block outputBlock, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock, 4)
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', Items.LEATHER)
                .pattern("434")
                .pattern("212")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }

    private void registerChip(Item chip, Item material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(chip, 4)
                .define('1', material)
                .define('2', Items.QUARTZ)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Tags.Items.INGOTS_IRON)
                .pattern("343")
                .pattern("212")
                .pattern("343")
                .unlockedBy("has_item", has(material))
                .save(consumer);
    }

    private void registerAdvancedPad(Block outputBlock, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock, 4)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', Items.LEATHER)
                .pattern("434")
                .pattern("212")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }

    private void registerModule(Item outputItem, Item chip, Item item, Consumer<FinishedRecipe> consumer) {
        registerModule(outputItem, chip, item, Items.AMETHYST_SHARD, consumer);
    }

    private void registerModule(Item outputItem, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.AMETHYST_SHARD)
                .define('4', item)
                .define('5', item2)
                .pattern("212")
                .pattern("343")
                .pattern("252")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }

    private void registerMachine(Block outputBlock, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE_BLOCK)
                .define('4', item)
                .define('5', item2)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(chip))
                .save(consumer);
    }

    private void registerNaniteTool(Item outputItem, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', ModItems.PORTABLE_ITEM_MENDER.get())
                .define('3', item)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(item))
                .save(consumer);
    }
}