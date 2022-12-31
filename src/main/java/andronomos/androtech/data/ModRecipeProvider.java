package andronomos.androtech.data;

import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        createChipRecipe(ModItems.BASIC_CHIP.get(), Items.GOLD_INGOT, consumer);
        createChipRecipe(ModItems.ADVANCED_CHIP.get(), Items.DIAMOND, consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BLOCK_GPS_MODULE.get())
                .requires(Items.IRON_INGOT, 1)
                .requires(Items.PAPER, 1)
                .requires(ModItems.BASIC_CHIP.get(), 1)
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.MOB_STASIS_MODULE.get())
                .requires(Items.IRON_INGOT, 1)
                .requires(Items.PAPER, 1)
                .requires(ModItems.BASIC_CHIP.get(), 1)
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.ITEM_ATTRACTOR_MODULE.get())
                .requires(Items.IRON_INGOT, 1)
                .requires(Items.ENDER_PEARL, 1)
                .requires(ModItems.BASIC_CHIP.get(), 1)
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }

    private void createChipRecipe(Item chip, Item material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(chip, 4)
                .define('1', material)
                .define('2', Items.QUARTZ)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Tags.Items.INGOTS_COPPER)
                .pattern("343")
                .pattern("212")
                .pattern("343")
                .unlockedBy("has_item", has(material))
                .save(consumer);
    }

    private void createBasicModuleRecipe(Item outputItem, Item chip, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', Items.IRON_INGOT)
                .define('2', Items.PAPER)
                .define('3', chip)
                .define('4', item)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }
}