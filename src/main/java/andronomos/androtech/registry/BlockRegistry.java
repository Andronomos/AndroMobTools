package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.itemattractor.ItemAttractorBlock;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.damagepad.DamagePadBlock;
import andronomos.androtech.block.pad.padeffect.PadEffect;
import andronomos.androtech.block.pad.padeffect.PadEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {
	private static Block.Properties PAD_PROPERTIES = Block.Properties.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
			.strength(5.0F)
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops();

	private static Block.Properties MACHINE_PROPERTIES = Block.Properties.copy(Blocks.IRON_BLOCK)
			.strength(5.0F)
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops();

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AndroTech.MODID);


	public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad", PadEffects.ACCELERATION_WEAK, true);
	public static final RegistryObject<Block> NORMAL_ACCELERATION_PAD = registerPad("normal_acceleration_pad", PadEffects.ACCELERATION_NORMAL, true);
	public static final RegistryObject<Block> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad", PadEffects.ACCELERATION_STRONG, true);
	public static final RegistryObject<Block> DAMAGE_PAD = registerBlock("damage_pad", () -> new DamagePadBlock(PAD_PROPERTIES));
	public static final RegistryObject<Block> ITEM_ATTRACTOR = registerBlock("item_attractor", () -> new ItemAttractorBlock(MACHINE_PROPERTIES));


	private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouldAffectPlayer) {
		return registerBlock(name, () -> new PadEffectBlock(PAD_PROPERTIES.noOcclusion(), effect, shouldAffectPlayer));
	}

	public static RegistryObject<Block> registerBlock(final String name, Block.Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}

	private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory) {
		final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);
		ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		return block;
	}
}
