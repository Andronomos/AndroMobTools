package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.*;
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

        registerModule(ModItems.MOB_TRANSPORT_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.MINECART, consumer);
        registerModule(ModItems.ITEM_ATTRACTOR_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, consumer);
        registerModule(ModItems.BLOCK_GPS_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.MAP, consumer);
        registerModule(ModItems.NIGHT_VISION_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.GOLDEN_CARROT, consumer);
        registerModule(ModItems.NANITE_MODULE.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, consumer);
        registerModule(ModItems.WATER_BREATHING_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.HEART_OF_THE_SEA, Items.TURTLE_HELMET, consumer);
        registerModule(ModItems.SWIFTNESS_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.RABBIT_FOOT, consumer);
        registerModule(ModItems.FIRE_RESISTANCE_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.BLAZE_POWDER, consumer);
        registerModule(ModItems.REGENERATION_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.GOLDEN_APPLE, consumer);

        registerNullifier(ModItems.POISON_NULLIFIER.get(), Items.POISONOUS_POTATO, Items.MILK_BUCKET, consumer);
        registerNullifier(ModItems.WITHER_NULLIFIER.get(), Items.WITHER_SKELETON_SKULL, Items.MILK_BUCKET, consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.REDSTONE_RECEIVER.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.OBSERVER)
                .define('4', Items.ENDER_PEARL)
                .define('5', Items.REDSTONE_BLOCK)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.REDSTONE_TRANSMITTER.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REPEATER)
                .define('4', Items.ENDER_PEARL)
                .define('5', Items.REDSTONE_BLOCK)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);

        registerAdvancedMachine(ModBlocks.MOB_CLONER.get(), ModItems.MOB_TRANSPORT_MODULE.get(), Items.SPAWNER, consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ITEM_INCINERATOR.get())
                .define('1', Items.IRON_INGOT)
                .define('2', Items.GLASS)
                .define('3', Items.LAVA_BUCKET)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ITEM_ATTRACTOR.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE)
                .define('4', Items.CHEST)
                .define('5', ModItems.ITEM_ATTRACTOR_MODULE.get())
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CROP_HARVESTER.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE)
                .define('4', Items.CHEST)
                .define('5', Items.DIAMOND_HOE)
                .define('6', Items.WATER_BUCKET)
                .pattern("212")
                .pattern("536")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        /** Pads **/
        registerPad(ModBlocks.MOB_KILLING_PAD.get(), ModItems.ADVANCED_CHIP.get(), Items.IRON_SWORD, consumer);
        registerPad(ModBlocks.WEAK_ACCELERATION_PAD.get(), ModItems.BASIC_CHIP.get(), Items.SUGAR, consumer);
        registerPad(ModBlocks.STRONG_ACCELERATION_PAD.get(), ModItems.ADVANCED_CHIP.get(), Items.RABBIT_FOOT, consumer);

        registerNaniteTool(ModItems.NANITE_ENHANCED_PICKAXE.get(), Items.NETHERITE_PICKAXE,  consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_AXE.get(), Items.NETHERITE_AXE, consumer);
        registerNaniteTool(ModItems.NANITE_ENHANCED_SHOVEL.get(), Items.NETHERITE_SHOVEL, consumer);
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

    private void registerPad(Block outputBlock, Item chip, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock, 4)
                .define('1', chip)
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .pattern("232")
                .pattern("313")
                .pattern("232")
                .unlockedBy("has_item", has(chip))
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

    private void registerNullifier(Item outputItem, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', item)
                .define('4', item2)
                .define('5', Items.NETHER_STAR)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }

    private void registerAdvancedMachine(Block outputBlock, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        registerMachine(outputBlock, ModItems.ADVANCED_CHIP.get(), item, item2, consumer);
    }

    private void registerMachine(Block outputBlock, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE)
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
                .define('2', ModItems.NANITE_MODULE.get())
                .define('3', item)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(item))
                .save(consumer);
    }
}