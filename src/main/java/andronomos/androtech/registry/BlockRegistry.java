package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.MachineBlock;
import andronomos.androtech.block.itemattractor.ItemAttractorBlock;
import andronomos.androtech.block.damagepad.DamagePadBlock;
import andronomos.androtech.block.FlatMachineBlock;
import andronomos.androtech.block.FlatMachineEffects;
import andronomos.androtech.block.collisioneffect.ICollisionEffect;
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
	private static final Block.Properties PAD_PROPERTIES = Block.Properties.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
			.strength(5.0F)
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops();

	private static final Block.Properties MACHINE_PROPERTIES = Block.Properties.copy(Blocks.IRON_BLOCK)
			.strength(5.0F)
			.sound(SoundType.METAL)
			.requiresCorrectToolForDrops();

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AndroTech.MODID);

	public static final RegistryObject<Block> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad",
			false,
			true,
			"weak_acceleration_pad_top",
			FlatMachineEffects.ACCELERATION_WEAK);

	public static final RegistryObject<Block> NORMAL_ACCELERATION_PAD = registerPad("normal_acceleration_pad", false,
			true,
			"normal_acceleration_pad_top",
			FlatMachineEffects.ACCELERATION_NORMAL);

	public static final RegistryObject<Block> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad",false,
			true,
			"strong_acceleration_pad_top",
			FlatMachineEffects.ACCELERATION_STRONG);

	//public static final RegistryObject<Block> WEAK_JUMP_PAD = registerPad("weak_jump_pad",false,
	//		false,
	//		"weak_jump_pad_top",
	//		FlatMachineEffects.JUMP_WEAK);

	public static final RegistryObject<Block> DAMAGE_PAD = registerBlock("damage_pad", () -> new DamagePadBlock(PAD_PROPERTIES));
	public static final RegistryObject<Block> ITEM_ATTRACTOR = registerBlock("item_attractor", () -> new ItemAttractorBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<Block> MACHINE_BLOCK = registerBlock("machine_block", () -> new MachineBlock(MACHINE_PROPERTIES, false, false, false));


	//private static <T extends Block> RegistryObject<Block> registerPad(String name, PadEffect effect, boolean shouldAffectPlayer) {
	//	return registerBlock(name, () -> new PadEffectBlock(PAD_PROPERTIES.noOcclusion(), effect, shouldAffectPlayer));
	//}

	private static <T extends Block> RegistryObject<Block> registerPad(String name, boolean hasToolTip, boolean isDirectional, String topTexture, ICollisionEffect effect) {
		return registerBlock(name, () -> new FlatMachineBlock(PAD_PROPERTIES.noOcclusion(), hasToolTip, isDirectional, topTexture, effect));
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
