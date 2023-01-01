package andronomos.androtech.data;

import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_PICKAXE.get(), Items.NETHERITE_PICKAXE,  consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_AXE.get(), Items.NETHERITE_AXE, consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_SHOVEL.get(), Items.NETHERITE_SHOVEL, consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_SWORD.get(), Items.NETHERITE_SWORD, consumer);
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

    private void createNaniteToolRecipe(Item outputItem, Item item, Consumer<FinishedRecipe> consumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(ModItems.MENDING_MODULE.get()), outputItem)
                .unlocks("has_mending_module", has(ModItems.MENDING_MODULE.get()))
                .save(consumer, outputItem + "_smithing");
    }

    //netheriteSmithing(p_176532_, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
    //protected static void netheriteSmithing(Consumer<FinishedRecipe> p_125995_, Item p_125996_, Item p_125997_) {
    //    UpgradeRecipeBuilder.smithing(Ingredient.of(p_125996_), Ingredient.of(Items.NETHERITE_INGOT), p_125997_).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(p_125995_, getItemName(p_125997_) + "_smithing");
    //}
}