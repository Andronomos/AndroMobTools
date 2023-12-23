package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AndroTech.MODID);


	public static RegistryObject<Block> registerBlock(final String name, Block.Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}

	private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory) {
		final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);
		andronomos.androtech.registry.ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		return block;
	}
}
