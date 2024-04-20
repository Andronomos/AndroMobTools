package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.base.MachineBlock;
import andronomos.androtech.block.TeleportInhibitorBlock;
import andronomos.androtech.block.mobrepulsor.MobRepulsorBlock;
import andronomos.androtech.block.itemattractor.ItemAttractorBlock;
import andronomos.androtech.block.mobkiller.MobKillerBlock;
import andronomos.androtech.block.FlatMachineBlock;
import andronomos.androtech.block.FlatMachineEffects;
import andronomos.androtech.block.collisioneffect.ICollisionEffect;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlock;
import andronomos.androtech.block.wirelessredstone.RedstoneSignalReceiverBlock;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlock;
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

	public static final RegistryObject<FlatMachineBlock> WEAK_ACCELERATION_PAD = registerPad("weak_acceleration_pad",
			FlatMachineEffects.ACCELERATION_WEAK);

	public static final RegistryObject<FlatMachineBlock> NORMAL_ACCELERATION_PAD = registerPad("normal_acceleration_pad",
			FlatMachineEffects.ACCELERATION_NORMAL);

	public static final RegistryObject<FlatMachineBlock> STRONG_ACCELERATION_PAD = registerPad("strong_acceleration_pad",
			FlatMachineEffects.ACCELERATION_STRONG);

	public static final RegistryObject<MachineBlock> MOB_KILLER = registerBlock("mob_killer", () -> new MobKillerBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> ITEM_ATTRACTOR = registerBlock("item_attractor", () -> new ItemAttractorBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> ITEM_INCINERATOR = registerBlock("item_incinerator", () -> new ItemIncineratorBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> REDSTONE_SIGNAL_RECEIVER = registerBlock("redstone_signal_receiver", () -> new RedstoneSignalReceiverBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> REDSTONE_SIGNAL_TRANSMITTER = registerBlock("redstone_signal_transmitter", () -> new RedstoneSignalTransmitterBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> TELEPORT_INHIBITOR = registerBlock("teleport_inhibitor", () -> new TeleportInhibitorBlock(MACHINE_PROPERTIES));
	public static final RegistryObject<MachineBlock> MOB_REPULSOR = registerBlock("mob_repulsor", () -> new MobRepulsorBlock(MACHINE_PROPERTIES));


	private static <T extends FlatMachineBlock> RegistryObject<FlatMachineBlock> registerPad(String name, ICollisionEffect effect) {
		return registerBlock(name, () -> new FlatMachineBlock(PAD_PROPERTIES.noOcclusion(), effect));
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
