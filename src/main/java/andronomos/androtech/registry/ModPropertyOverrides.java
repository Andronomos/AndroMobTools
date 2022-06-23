package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.device.base.ToggleableDevice;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModPropertyOverrides {
	public static final ResourceLocation IS_ACTIVATED = new ResourceLocation(AndroTech.MOD_ID, "activated");

	public static void register() {
		ItemProperties.register(ModItems.MOB_CLONING_MODULE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackUtil.hasEntityTag(stack) ? 1 : 0);

		ItemProperties.register(ModItems.BLOCK_GPS_RECORDER.get(),
				IS_ACTIVATED, (stack, level, living, id) -> ItemStackUtil.getBlockPos(stack) != null ? 1 : 0);

		registerActivatableItem(ModItems.PORTABLE_ITEM_ATTRACTOR.get());
		registerActivatableItem(ModItems.PORTABLE_ITEM_MENDER.get());
		registerActivatableItem(ModItems.SWIFTNESS_EMITTER.get());
		registerActivatableItem(ModItems.SWIFTNESS_EMITTER.get());
		registerActivatableItem(ModItems.REGENERATION_EMITTER.get());
		registerActivatableItem(ModItems.FIRE_RESISTANCE_EMITTER.get());
		registerActivatableItem(ModItems.NIGHT_VISION_EMITTER.get());
		registerActivatableItem(ModItems.WATER_BREATHING_EMITTER.get());
		registerActivatableItem(ModItems.POISON_NULLIFIER.get());
		registerActivatableItem(ModItems.WITHER_NULLIFIER.get());
	}

	private static void registerActivatableItem(Item item) {
		ItemProperties.register(item, IS_ACTIVATED, (stack, level, living, id) -> stackIsActivated(stack) ? 1 : 0);
	}

	private static boolean stackIsActivated(ItemStack stack) {
		Item item = stack.getItem();

		if(item instanceof ToggleableDevice toggleableDevice) {
			return toggleableDevice.isActivated(stack);
		}

		return false;
	}
}
