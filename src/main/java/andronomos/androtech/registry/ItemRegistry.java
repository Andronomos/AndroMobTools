package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.item.FakeSword;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AndroTech.MODID);

	public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword",
			() -> new FakeSword(new Item.Properties()));
}
