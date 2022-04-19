package andronomos.androtech.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SwiftnessPotionIngredient extends Ingredient {
	protected SwiftnessPotionIngredient(Stream<? extends Value> p_43907_) {
		super(p_43907_);
	}

	@Override
	public boolean test(@Nullable ItemStack stack) {
		if(!(stack.getItem() instanceof PotionItem)) {
			return false;
		}






		return true;
	}
}
