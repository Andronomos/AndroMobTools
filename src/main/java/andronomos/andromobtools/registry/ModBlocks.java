package andronomos.andromobtools.registry;


import andronomos.andromobtools.AndroMobTools;
import andronomos.andromobtools.block.MobClonerBlock;
import andronomos.andromobtools.block.LootAttractorBlock;
import andronomos.andromobtools.block.LootIncineratorBlock;
import andronomos.andromobtools.block.pad.MobKillingPadBlock;
import andronomos.andromobtools.block.pad.emiiter.EmitterPadBlock;
import andronomos.andromobtools.block.pad.emiiter.effect.PadEffect;
import andronomos.andromobtools.block.pad.emiiter.effect.PadEffects;
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

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AndroMobTools.MOD_ID);

    public static final RegistryObject<Block> MOB_CLONER = registerBlock("mob_cloner", () -> new MobClonerBlock(MACHINE_PROPERTIES), ModItems.BASE_PROPERTIES);
    public static final RegistryObject<Block> LOOT_ATTRACTOR = registerBlock("loot_attractor", () -> new LootAttractorBlock(MACHINE_PROPERTIES), ModItems.BASE_PROPERTIES);
    public static final RegistryObject<Block> LOOT_INCINERATOR = registerBlock("loot_incinerator", () -> new LootIncineratorBlock(MACHINE_PROPERTIES), ModItems.BASE_PROPERTIES);
    public static final RegistryObject<Block> MOB_KILLING_PAD = registerBlock("mob_killing_pad", () -> new MobKillingPadBlock(MACHINE_PROPERTIES), ModItems.BASE_PROPERTIES);
    public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad", PadEffects.ACCELERATION_WEAK, false);
    public static final RegistryObject<Block> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad", PadEffects.ACCELERATION_STRONG, false);


    private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouledAffectPlayer) {
        return registerBlock(name, () -> new EmitterPadBlock(MACHINE_PROPERTIES.noOcclusion(), effect, shouledAffectPlayer), ModItems.BASE_PROPERTIES);
    }

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
