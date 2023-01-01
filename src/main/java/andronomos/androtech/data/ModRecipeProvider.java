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

        ShapedRecipeBuilder.shaped(ModItems.CHIP_WAFER.get())
                .define('1', Items.COPPER_INGOT)
                .define('2', Items.CLAY_BALL)
                .define('3', Items.REDSTONE)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        createChipRecipe(ModItems.BASIC_CHIP.get(), Items.IRON_INGOT, consumer);
        createChipRecipe(ModItems.ADVANCED_CHIP.get(), Items.GOLD_INGOT, consumer);
        createChipRecipe(ModItems.ELITE_CHIP.get(), Items.DIAMOND, consumer);

        createModuleRecipe(ModItems.BLOCK_GPS_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.PAPER, consumer);
        createModuleRecipe(ModItems.MOB_STASIS_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.LEAD, consumer);
        createModuleRecipe(ModItems.ITEM_ATTRACTOR_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, consumer);
        createModuleRecipe(ModItems.MENDING_MODULE.get(), ModItems.ELITE_CHIP.get(), Items.NETHER_STAR, consumer);
    }

    private void createChipRecipe(Item chip, Item material, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(chip, 4)
                .requires(material, 1)
                .requires(ModItems.CHIP_WAFER.get(), 1)
                .requires(Items.REDSTONE, 1)
                .requires(Items.SLIME_BALL, 1)
                .unlockedBy("has_item", has(material))
                .save(consumer);
    }

    private void createModuleRecipe(Item outputItem, Item chip, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', Items.IRON_INGOT)
                .define('2', chip)
                .define('3', Items.AMETHYST_SHARD)
                .define('4', Items.GLASS_PANE)
                .define('5', item)
                .pattern("141")
                .pattern("353")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }
}