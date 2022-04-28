package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.activatableItem.AbstractActivatableItem;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModPropertyOverrides {
	public static final ResourceLocation IS_ACTIVATED = new ResourceLocation(AndroTech.MOD_ID, "activated");

	public static void register() {
		ItemProperties.register(ModItems.MOB_TRANSPORTER_MODULE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> {
					return ItemStackUtil.containsEntity(stack) ? 1 : 0;
				});

		ItemProperties.register(ModItems.BLOCK_GPS_MODULE.get(),
				IS_ACTIVATED, (stack, level, living, id) -> {
					return NBTUtil.getItemStackBlockPos(stack) != null ? 1 : 0;
				});

		registerActivatableItem(ModItems.ATTRACTOR_MODULE.get());
		registerActivatableItem(ModItems.SWIFTNESS_EMITTER.get());
		registerActivatableItem(ModItems.SWIFTNESS_EMITTER.get());
		registerActivatableItem(ModItems.REGENERATION_EMITTER.get());
		registerActivatableItem(ModItems.FIRE_RESISTANCE_EMITTER.get());
		registerActivatableItem(ModItems.POISON_NULLIFIER.get());
		registerActivatableItem(ModItems.WITHER_NULLIFIER.get());
		registerActivatableItem(ModItems.NANITE_MODULE.get());
	}

	private static void registerActivatableItem(Item item) {
		ItemProperties.register(item, IS_ACTIVATED, (stack, level, living, id) -> stackIsActivated(stack) ? 1 : 0);
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
