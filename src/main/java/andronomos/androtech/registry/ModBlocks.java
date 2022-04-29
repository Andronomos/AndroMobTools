package andronomos.androtech.registry;


import andronomos.androtech.block.*;
import andronomos.androtech.block.pad.MobKillingPadBlock;
import andronomos.androtech.block.pad.emiiter.EmitterPadBlock;
import andronomos.androtech.block.pad.emiiter.effect.PadEffect;
import andronomos.androtech.block.pad.emiiter.effect.PadEffects;
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

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block", () -> new TestBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());

    public static final RegistryObject<Block> MOB_CLONER = registerBlock("mob_cloner", () -> new MobClonerBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ITEM_ATTRACTOR = registerBlock("item_attractor", () -> new ItemAttractorBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ITEM_INCINERATOR = registerBlock("item_incinerator", () -> new ItemIncineratorBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> MOB_KILLING_PAD = registerBlock("mob_killing_pad", () -> new MobKillingPadBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad", PadEffects.ACCELERATION_WEAK, false);
    public static final RegistryObject<Block> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad", PadEffects.ACCELERATION_STRONG, false);
    public static final RegistryObject<Block> FARMER_UNIT = registerBlock("farmer_unit", () -> new FarmerUnit(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> WIRELESS_LIGHT = registerBlock("wireless_light", () -> new WirelessLightBlock(MACHINE_PROPERTIES), ModItems.DEBUG_PROPERTIES);
    public static final RegistryObject<Block> REDSTONE_TRANSMITTER = registerBlock("redstone_transmitter", () -> new RedstoneTransmitterBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> REDSTONE_RECEIVER = registerBlock("redstone_receiver", () -> new RedstoneReceiverBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());

    private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouledAffectPlayer) {
        return registerBlock(name, () -> new EmitterPadBlock(MACHINE_PROPERTIES.noOcclusion(), effect, shouledAffectPlayer), ModItems.GetBaseProperties());
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
