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
        registerEquipment(ModItems.MOB_STORAGE_CELL.get(), ModItems.BASIC_CHIP.get(), Items.REDSTONE, Items.LEAD, consumer);
        registerEquipment(ModItems.PORTABLE_LOOT_ATTRACTOR.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.HOPPER, consumer);
        registerEquipment(ModItems.SPEED_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.SUGAR, Items.BEACON, consumer);

        /** Machines **/
        registerBasicMachine(ModBlocks.LOOT_ATTRACTOR.get(), Items.CHEST, ModItems.PORTABLE_LOOT_ATTRACTOR.get(), consumer);
        registerBasicMachine(ModBlocks.LOOT_INCINERATOR.get(), Items.GLASS, Items.LAVA_BUCKET, consumer);
        registerBasicMachine(ModBlocks.REDSTONE_RECEIVER.get(), Items.ENDER_PEARL, Items.REDSTONE_BLOCK, consumer);
        registerBasicMachine(ModBlocks.REDSTONE_TRANSMITTER.get(), Items.ENDER_PEARL, Items.REDSTONE_BLOCK, consumer);

        registerAdvancedMachine(ModBlocks.MOB_CLONER.get(), ModItems.MOB_STORAGE_CELL.get(), Items.SPAWNER, consumer);
        registerAdvancedMachine(ModBlocks.CROP_HARVESTER.get(), Items.DIAMOND_HOE, Items.WATER_BUCKET, consumer);

        registerAdvancedContainerMachine(ModBlocks.CROP_HARVESTER.get(), Items.DIAMOND_HOE, Items.WATER_BUCKET, consumer);

        /** Pads **/
        registerPad(ModBlocks.MOB_KILLING_PAD.get(), ModItems.ADVANCED_CHIP.get(), Items.IRON_SWORD, consumer);
        registerPad(ModBlocks.WEAK_ACCELERATION_PAD.get(), ModItems.BASIC_CHIP.get(), Items.SUGAR, consumer);
        registerPad(ModBlocks.STRONG_ACCELERATION_PAD.get(), ModItems.BASIC_CHIP.get(), Items.RABBIT_FOOT, consumer);
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
                .group(RecipeBookCategories.CRAFTING_EQUIPMENT.name())
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
                .unlockedBy("has_item", has(item))
                .group(RecipeBookCategories.CRAFTING_MISC.name())
                .save(consumer);
    }

    private void registerBasicMachine(Block outputBlock, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        registerMachine(outputBlock, ModItems.BASIC_CHIP.get(), item, item2, consumer);
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
                .unlockedBy("has_item", has(item2))
                .group(RecipeBookCategories.CRAFTING_MISC.name())
                .save(consumer);
    }

    private void registerBasicContainerMachine(Block outputBlock, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        registerContainerMachine(outputBlock, ModItems.BASIC_CHIP.get(), item, item2, consumer);
    }

    private void registerAdvancedContainerMachine(Block outputBlock, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        registerContainerMachine(outputBlock, ModItems.ADVANCED_CHIP.get(), item, item2, consumer);
    }

    private void registerContainerMachine(Block outputBlock, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE)
                .define('4', item)
                .define('5', item)
                .define('6', Items.CHEST)
                .pattern("212")
                .pattern("435")
                .pattern("262")
                .unlockedBy("has_item", has(item))
                .group(RecipeBookCategories.CRAFTING_MISC.name())
                .save(consumer);
    }
}