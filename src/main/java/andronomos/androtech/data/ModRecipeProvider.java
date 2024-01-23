package andronomos.androtech.data;

import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
	public ModRecipeProvider(PackOutput output) {
		super(output);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
		generateChipRecipe(ItemRegistry.BASIC_CHIP.get(), Items.IRON_INGOT, recipeConsumer);
		generateChipRecipe(ItemRegistry.ADVANCED_CHIP.get(), Items.GOLD_INGOT, recipeConsumer);
		generateChipRecipe(ItemRegistry.ELITE_CHIP.get(), Items.DIAMOND, recipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.DAMAGE_PAD.get(), 1)
				.define('1', Tags.Items.INGOTS_IRON)
				.define('2', Items.DIAMOND_SWORD)
				.define('3', ItemRegistry.ELITE_CHIP.get())
				.pattern(" 2 ")
				.pattern("232")
				.pattern("111")
				.unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
				.save(recipeConsumer);

		generateAccelerationPadRecipe(BlockRegistry.WEAK_ACCELERATION_PAD.get(), ItemRegistry.BASIC_CHIP.get(), recipeConsumer);
		generateAccelerationPadRecipe(BlockRegistry.NORMAL_ACCELERATION_PAD.get(), ItemRegistry.ADVANCED_CHIP.get(), recipeConsumer);
		generateAccelerationPadRecipe(BlockRegistry.STRONG_ACCELERATION_PAD.get(), ItemRegistry.ELITE_CHIP.get(), recipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.REDSTONE_SIGNAL_RECEIVER.get(), 1)
				.define('1', Tags.Items.INGOTS_IRON)
				.define('2', Items.REPEATER)
				.define('3', Items.ENDER_PEARL)
				.define('4', ItemRegistry.BASIC_CHIP.get())
				.pattern("131")
				.pattern("323")
				.pattern("141")
				.unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.REDSTONE_SIGNAL_TRANSMITTER.get(), 1)
				.define('1', Tags.Items.INGOTS_IRON)
				.define('2', Items.ENDER_PEARL)
				.define('3', Items.REDSTONE_TORCH)
				.define('4', ItemRegistry.BASIC_CHIP.get())
				.pattern("121")
				.pattern("232")
				.pattern("141")
				.unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
				.save(recipeConsumer);

		generateDeviceRecipe(ItemRegistry.GPS_MODULE.get(), Items.COMPASS, ItemRegistry.BASIC_CHIP.get(), recipeConsumer);
		generateDeviceRecipe(ItemRegistry.SHARPNESS_AUGMENT.get(), Items.IRON_SWORD, ItemRegistry.ADVANCED_CHIP.get(), recipeConsumer);
		generateDeviceRecipe(ItemRegistry.FIRE_AUGMENT.get(), Items.BLAZE_POWDER, ItemRegistry.ADVANCED_CHIP.get(), recipeConsumer);
		generateDeviceRecipe(ItemRegistry.LOOTING_AUGMENT.get(), Items.RABBIT_FOOT, ItemRegistry.ADVANCED_CHIP.get(), recipeConsumer);
		//generateDeviceRecipe(ItemRegistry.SMITE_AUGMENT.get(), Items.IRON_SWORD, ItemRegistry.ADVANCED_CHIP.get(), recipeConsumer);
	}

	private void generateAccelerationPadRecipe(Block output, Item chip, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 6)
				.define('1', Tags.Items.INGOTS_IRON)
				.define('2', chip)
				.define('3', StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS)))
				.pattern("   ")
				.pattern("323")
				.pattern("111")
				.unlockedBy("has_item", has(chip))
				.save(consumer);
	}

	private void generateDeviceRecipe(Item output, Item item, Item chip, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
				.define('1', Items.IRON_INGOT)
				.define('2', chip)
				.define('3', Items.GLASS_PANE)
				.define('4', item)
				.define('5', Items.AMETHYST_SHARD)
				.define('6', Items.REDSTONE)
				.pattern("636")
				.pattern("545")
				.pattern("121")
				.unlockedBy("has_item", has(chip))
				.save(consumer);
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
		String blockName = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(output)).getPath();
		SingleItemRecipeBuilder stonecutting = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, amount);
		stonecutting.unlockedBy("has_item", has(input));
		stonecutting.save(consumer, blockName + "_from_stonecutting");
	}
}
