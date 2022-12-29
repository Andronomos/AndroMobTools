package andronomos.androtech.data;

import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
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

        createChipRecipe(ModItems.BASIC_CHIP.get(), Items.GOLD_INGOT, consumer);
        createChipRecipe(ModItems.ADVANCED_CHIP.get(), Items.DIAMOND, consumer);
        createBasicModuleRecipe(ModItems.BLOCK_GPS_MODULE.get(), ModItems.BASIC_CHIP.get(), consumer);
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

    private void createBasicModuleRecipe(Item outputItem, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', Items.IRON_INGOT)
                .define('2', Items.PAPER)
                .define('3', item)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }
}