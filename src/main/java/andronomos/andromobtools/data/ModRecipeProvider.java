package andronomos.andromobtools.data;

import andronomos.andromobtools.registry.ModBlocks;
import andronomos.andromobtools.registry.ModItems;
import net.minecraft.data.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        /**
         *
         * Crafting components
         *
         **/
        ShapedRecipeBuilder.shaped(ModItems.BASIC_CHIP.get(), 4)
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.QUARTZ)
                .define('3', Items.REDSTONE)
                .pattern("313")
                .pattern("212")
                .pattern("313")
                .unlockedBy("has_item", has(Items.GOLD_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.ADVANCED_CHIP.get(), 4)
                .define('1', Items.DIAMOND)
                .define('2', Items.QUARTZ)
                .define('3', Items.REDSTONE)
                .pattern("313")
                .pattern("212")
                .pattern("313")
                .unlockedBy("has_item", has(Items.DIAMOND))
                .save(consumer);

        /**
         *
         * Equipment
         *
         **/
        ShapedRecipeBuilder.shaped(ModItems.MOB_STORAGE_CELL.get(), 1)
                .define('1', Items.IRON_INGOT)
                .define('2', Items.REDSTONE)
                .define('3', ModItems.BASIC_CHIP.get())
                .define('4', Items.LEAD)
                .pattern("121")
                .pattern("343")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LEAD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.PORTABLE_LOOT_ATTRACTOR.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.ENDER_PEARL)
                .pattern(" 31")
                .pattern(" 23")
                .pattern("2  ")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);


        /**
         *
         * Machines
         *
         **/
        ShapedRecipeBuilder.shaped(ModBlocks.MOB_CLONER.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.SPAWNER)
                .define('4', Items.REDSTONE)
                .pattern("212")
                .pattern("434")
                .pattern("212")
                .unlockedBy("has_item", has(Items.SPAWNER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LOOT_ATTRACTOR.get())
                .define('1', ModItems.PORTABLE_LOOT_ATTRACTOR.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Tags.Items.CHESTS)
                .define('4', Items.REDSTONE)
                .define('5', ModItems.ADVANCED_CHIP.get())
                .pattern("252")
                .pattern("414")
                .pattern("232")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LOOT_INCINERATOR.get())
                .define('1', Items.LAVA_BUCKET)
                .define('2', Items.IRON_INGOT)
                .define('3', Tags.Items.CHESTS)
                .define('4', Tags.Items.GLASS)
                .pattern("242")
                .pattern("414")
                .pattern("232")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.MOB_KILLING_PAD.get(), 4)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.DIAMOND_SWORD)
                .pattern("   ")
                .pattern("131")
                .pattern("222")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.WEAK_ACCELERATION_PAD.get(), 4)
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.SUGAR)
                .pattern("   ")
                .pattern("131")
                .pattern("222")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.STRONG_ACCELERATION_PAD.get(), 4)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', Items.RABBIT_FOOT)
                .pattern("   ")
                .pattern("131")
                .pattern("222")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);
    }
}