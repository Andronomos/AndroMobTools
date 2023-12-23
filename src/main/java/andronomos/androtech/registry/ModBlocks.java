package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.pad.PadEffectBlock;
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

public class ModBlocks {
	private static Block.Properties PAD_PROPERTIES = Block.Properties.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
			.strength(5.0F)
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops();

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AndroTech.MODID);


	public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad", PadEffects.ACCELERATION_WEAK, false);



	private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouledAffectPlayer) {
		return registerBlock(name, () -> new PadEffectBlock(PAD_PROPERTIES.noOcclusion(), effect, shouledAffectPlayer));
	}

	public static RegistryObject<Block> registerBlock(final String name, Block.Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}

	private static <BLOCK extends Block> RegistryObject<BLOCK> registerBlock(final String name, final Supplier<BLOCK> blockFactory) {
		final RegistryObject<BLOCK> block = BLOCKS.register(name, blockFactory);
		andronomos.androtech.registry.ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		return block;
	}
}
