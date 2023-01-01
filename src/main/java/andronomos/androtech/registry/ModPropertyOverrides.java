package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.base.ToggleableDevice;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModPropertyOverrides {
	public static final ResourceLocation IS_ACTIVATED = new ResourceLocation(AndroTech.MOD_ID, "activated");

	public static void register() {
		ItemProperties.register(ModItems.BLOCK_GPS_MODULE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackUtils.getBlockPos(stack) != null ? 1 : 0);

		ItemProperties.register(ModItems.MOB_STASIS_MODULE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackUtils.hasEntityTag(stack) ? 1 : 0);

		registerToggleableItem(ModItems.ITEM_ATTRACTOR_MODULE.get());
		registerToggleableItem(ModItems.MENDING_MODULE.get());
	}

	private static void registerToggleableItem(Item item) {
		ItemProperties.register(item, IS_ACTIVATED, (stack, level, living, id) -> stackIsActivated(stack) ? 1 : 0);
	}

	private static boolean stackIsActivated(ItemStack stack) {
		Item item = stack.getItem();

		if(item instanceof ToggleableDevice toggleableModule) {
			return toggleableModule.isActivated(stack);
		}

		return false;
	}
}
