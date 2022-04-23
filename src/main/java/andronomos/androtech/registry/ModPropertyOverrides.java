package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.AbstractActivatableItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModPropertyOverrides {
	public static final ResourceLocation IS_ACTIVATED = new ResourceLocation(AndroTech.MOD_ID, "activated");

	public static void register() {
		registerActivatableItem(ModItems.PORTABLE_LOOT_ATTRACTOR.get());
		registerActivatableItem(ModItems.SPEED_EMITTER.get());
		registerActivatableItem(ModItems.SPEED_EMITTER.get());
		registerActivatableItem(ModItems.REGENERATION_EMITTER.get());
		registerActivatableItem(ModItems.FIRE_RESISTANCE_EMITTER.get());
		registerActivatableItem(ModItems.POISON_NULLIFIER.get());
		registerActivatableItem(ModItems.WITHER_NULLIFIER.get());
	}

	private static void registerActivatableItem(Item item) {
		ItemProperties.register(item,
				IS_ACTIVATED, (stack, level, living, id) -> {
					return stackIsActivated(stack) ? 1 : 0;
				});
	}

	private static boolean stackIsActivated(ItemStack stack) {
		Item item = stack.getItem();

		if(item instanceof AbstractActivatableItem) {
			if(((AbstractActivatableItem)item).isActivated(stack)) {
				return true;
			}
		}

		return false;
	}
}
