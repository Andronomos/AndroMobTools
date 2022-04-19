package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.client.RecipeBookCategories;
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

        /** * Crafting components **/
        registerChip(ModItems.BASIC_CHIP.get(), Tags.Items.INGOTS_GOLD, consumer);
        registerChip(ModItems.ADVANCED_CHIP.get(), Tags.Items.GEMS_DIAMOND, consumer);

        /** Equipment **/
        registerEquipment(ModItems.MOB_STORAGE_CELL.get(), ModItems.BASIC_CHIP.get(), Tags.Items.DUSTS_REDSTONE, Items.LEAD, consumer);
        registerEquipment(ModItems.PORTABLE_LOOT_ATTRACTOR.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.HOPPER, consumer);
        registerEquipment(ModItems.SPEED_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.SUGAR, Items.BEACON, consumer);

        /** Machines **/
        ShapedRecipeBuilder.shaped(ModBlocks.MOB_CLONER.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Items.SPAWNER)
                .pattern("212")
                .pattern("343")
                .pattern("232")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LOOT_ATTRACTOR.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Tags.Items.CHESTS)
                .define('5', ModItems.PORTABLE_LOOT_ATTRACTOR.get())
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LOOT_INCINERATOR.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Items.LAVA_BUCKET)
                .pattern("212")
                .pattern("343")
                .pattern("232")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CROP_HARVESTER.get())
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Items.DIAMOND_HOE)
                .define('5', Items.WATER_BUCKET)
                .define('6', Tags.Items.CHESTS)
                .pattern("212")
                .pattern("465")
                .pattern("232")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .group(RecipeBookCategories.CRAFTING_MISC.name())
                .save(consumer);

        registerPad(ModBlocks.MOB_KILLING_PAD.get(), ModItems.ADVANCED_CHIP.get(), Items.IRON_SWORD, consumer);
        registerPad(ModBlocks.WEAK_ACCELERATION_PAD.get(), ModItems.BASIC_CHIP.get(), Items.SUGAR, consumer);
        registerPad(ModBlocks.STRONG_ACCELERATION_PAD.get(), ModItems.BASIC_CHIP.get(), Items.RABBIT_FOOT, consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.REDSTONE_RECEIVER.get())
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', Items.REDSTONE_BLOCK)
                .pattern("212")
                .pattern("232")
                .pattern("252")
                .unlockedBy("has_item", has(ModItems.ADVANCED_CHIP.get()))
                .group(RecipeBookCategories.CRAFTING_MISC.name())
                .save(consumer);

        //registerbasicMachine(ModBlocks.REDSTONE_RECEIVER.get(), consumer);
    }

    private void registerChip(Item chip, Tags.IOptionalNamedTag<Item> material, Consumer<FinishedRecipe> consumer) {
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

    private void registerEquipment(Item output, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(output, 4)
                .define('1', chip)
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', item2)
                .pattern("212")
                .pattern("343")
                .pattern("232")
                .unlockedBy("has_item", has(item2))
                .save(consumer);
    }

    private void registerEquipment(Item output, Item chip, Tags.IOptionalNamedTag<Item> item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(output, 4)
                .define('1', chip)
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', item2)
                .pattern("212")
                .pattern("343")
                .pattern("232")
                .unlockedBy("has_item", has(item2))
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
                .save(consumer);
    }

    private void registerbasicMachine(Block outputBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock)
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .pattern("212")
                .pattern("   ")
                .pattern("2 2")
                .save(consumer);
    }

    private void registerAdvancedMachine(Consumer<FinishedRecipe> consumer) {

    }
}