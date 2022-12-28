package andronomos.androtech.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    private static Block.Properties PROPERTIES = Block.Properties.of(Material.STONE)
            .strength(3.0F, 3.0F)
            .sound(SoundType.STONE);

    private static Block.Properties MACHINE_PROPERTIES = Block.Properties.of(Material.STONE)
            .strength(5.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, andronomos.androtech.AndroTech.MOD_ID);










    private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory, Item.Properties properties) {
        return registerBlock(name, blockFactory, block -> new BlockItem(block, properties));
    }

    private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory, final IBlockItemFactory<BLOCK> itemFactory) {
        final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);
        ModItems.ITEMS.register(name, () -> itemFactory.create(block.get()));
        return block;
    }

    /**
     * A factory function used to create block items.
     *
     * @param <BLOCK> The block type
     */
    @FunctionalInterface
    public interface IBlockItemFactory<BLOCK> {
        Item create(BLOCK block);
    }
}
