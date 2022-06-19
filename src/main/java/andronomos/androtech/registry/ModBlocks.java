package andronomos.androtech.registry;

import andronomos.androtech.block.ItemIncinerator.ItemIncinerator;
import andronomos.androtech.block.amethystharvester.AmethystHarvester;
import andronomos.androtech.block.animalfarmer.AnimalFarmer;
import andronomos.androtech.block.cropfarmer.CropFarmer;
import andronomos.androtech.block.itemattractor.ItemAttractor;
import andronomos.androtech.block.itemmender.ItemMender;
import andronomos.androtech.block.mobcloner.MobCloner;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.padeffect.PadEffect;
import andronomos.androtech.block.pad.padeffect.PadEffects;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadBlock;
import andronomos.androtech.block.redstonereceiver.RedstoneReceiver;
import andronomos.androtech.block.redstonetransmitter.RedstoneTransmitter;
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

    public static final RegistryObject<Block> MOB_CLONER = registerBlock("mob_cloner", () -> new MobCloner(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ITEM_ATTRACTOR = registerBlock("item_attractor", () -> new ItemAttractor(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ITEM_INCINERATOR = registerBlock("item_incinerator", () -> new ItemIncinerator(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ANIMAL_FARMER = registerBlock("animal_farmer", () -> new AnimalFarmer(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> CROP_FARMER = registerBlock("crop_farmer", () -> new CropFarmer(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> ITEM_MENDER = registerBlock("item_mender", () -> new ItemMender(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> MOB_KILLING_PAD = registerBlock("mob_killing_pad", () -> new MobKillingPadBlock(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad", PadEffects.ACCELERATION_WEAK, false);
    public static final RegistryObject<Block> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad", PadEffects.ACCELERATION_STRONG, false);
    public static final RegistryObject<Block> REDSTONE_TRANSMITTER = registerBlock("redstone_transmitter", () -> new RedstoneTransmitter(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> REDSTONE_RECEIVER = registerBlock("redstone_receiver", () -> new RedstoneReceiver(MACHINE_PROPERTIES), ModItems.GetBaseProperties());
    public static final RegistryObject<Block> AMETHYST_HARVESTER = registerBlock("redstone_receiver", () -> new AmethystHarvester(MACHINE_PROPERTIES), ModItems.GetBaseProperties());


    private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouledAffectPlayer) {
        return registerBlock(name, () -> new PadEffectBlock(MACHINE_PROPERTIES.noOcclusion(), effect, shouledAffectPlayer), ModItems.GetBaseProperties());
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
