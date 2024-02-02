package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.MultiStateItem;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PropertyOverrideRegistry {
	public static final ResourceLocation IS_ACTIVATED = new ResourceLocation(AndroTech.MODID, "activated");

	public static void register() {
		ItemProperties.register(ItemRegistry.GPS_RECORDER.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackHelper.getBlockPos(stack) != null ? 1 : 0);

		ItemProperties.register(ItemRegistry.MOB_STORAGE_DEVICE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackHelper.hasEntityTag(stack) ? 1 : 0);

		registerMultiStateItem(ItemRegistry.ITEM_ATTRACTION_EMITTER.get());
		//registerMultiStateItem(ItemRegistry.MENDING_MODULE.get());
	}

	private static void registerMultiStateItem(Item item) {
		ItemProperties.register(item, IS_ACTIVATED, (stack, level, living, id) -> stackIsActivated(stack) ? 1 : 0);
	}

	private static boolean stackIsActivated(ItemStack stack) {
		Item item = stack.getItem();
		if(item instanceof MultiStateItem multiStateItem) {
			return multiStateItem.isActivated(stack);
		}
		return false;
	}
}
