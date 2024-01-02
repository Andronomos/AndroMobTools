package andronomos.androtech.data;

import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
	public ModRecipeProvider(PackOutput output) {
		super(output);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> recipeConsumer) {
		generateChipRecipe(ItemRegistry.BASIC_CHIP.get(), Items.IRON_INGOT, recipeConsumer);
		generateChipRecipe(ItemRegistry.ADVANCED_CHIP.get(), Items.GOLD_INGOT, recipeConsumer);
		generateChipRecipe(ItemRegistry.ELITE_CHIP.get(), Items.DIAMOND, recipeConsumer);

		//ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.STRONG_ACCELERATION_PAD.get(), 4)
		//		.define('1', Tags.Items.INGOTS_IRON)
		//		.pattern("000")
		//		.pattern("000")
		//		.pattern("111")
		//		.unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
		//		.save(recipeConsumer);
	}

	private void generateChipRecipe(Item output, Item item, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
				.define('1', Items.COPPER_INGOT)
				.define('2', Items.SLIME_BALL)
				.define('3', Items.REDSTONE)
				.define('4', Items.QUARTZ)
				.define('5', Items.AMETHYST_SHARD)
				.define('6', item)
				.pattern("414")
				.pattern("365")
				.pattern("424")
				.unlockedBy("has_item", has(Items.REDSTONE))
				.save(consumer);
	}

	private void generateSingleItemShapelessRecipe(Block output, Block sourceBlock, Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,
				output, 1);
		shapeless.requires(sourceBlock.asItem());
		shapeless.unlockedBy("has_item", has(sourceBlock));
		shapeless.save(consumer);
	}

	private void generateThreeByTwoRecipe(Block output, Block input, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder shaped = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6);
		shaped.define('#', input);
		shaped.pattern("###");
		shaped.pattern("###");
		shaped.unlockedBy("has_item", has(input));
		shaped.save(consumer);
	}

	private void generateStoneCutterRecipe(Block output, Block input, int amount, Consumer<FinishedRecipe> consumer) {
		String blockName = ForgeRegistries.BLOCKS.getKey(output).getPath();
		SingleItemRecipeBuilder stonecutting = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, amount);
		stonecutting.unlockedBy("has_item", has(input));
		stonecutting.save(consumer, blockName + "_from_stonecutting");
	}

	private void generateSlabRecipe(SlabBlock output, Item input, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder shaped = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6);
		shaped.define('#', input);
		shaped.pattern("###");
		shaped.unlockedBy("has_item", has(input));
		shaped.save(consumer);
	}

	private void generateStairRecipe(StairBlock output, Item input, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder shaped = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4);
		shaped.define('#', input);
		shaped.pattern("#  ");
		shaped.pattern("## ");
		shaped.pattern("###");
		shaped.unlockedBy("has_item", has(input));
		shaped.save(consumer);
	}
}
