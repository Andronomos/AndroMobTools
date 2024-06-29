package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.entityrepulsor.EntityRepulsorBlockEntity;
import andronomos.androtech.block.entityvacuum.EntityVacuumBlockEntity;
import andronomos.androtech.block.mobkiller.MobKillerBlockEntity;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlockEntity;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AndroTech.MODID);

	public static final RegistryObject<BlockEntityType<MobKillerBlockEntity>> MOB_KILLER_BE = BLOCK_ENTITIES
			.register("mob_killer_be", () -> BlockEntityType.Builder
			.of(MobKillerBlockEntity::new, BlockRegistry.MOB_KILLER.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<EntityVacuumBlockEntity>> ENTITY_VACUUM_BE = BLOCK_ENTITIES
			.register("entity_vacuum_be", () -> BlockEntityType.Builder
					.of(EntityVacuumBlockEntity::new, BlockRegistry.ENTITY_VACUUM.get())
					.build(null));

	public static final RegistryObject<BlockEntityType<RedstoneSignalTransmitterBlockEntity>> REDSTONE_SIGNAL_TRANSMITTER_BE = BLOCK_ENTITIES
			.register("redstone_signal_transmitter_be", () -> BlockEntityType.Builder
					.of(RedstoneSignalTransmitterBlockEntity::new, BlockRegistry.REDSTONE_SIGNAL_TRANSMITTER.get())
					.build(null));

	public static final RegistryObject<BlockEntityType<ItemIncineratorBlockEntity>> ITEM_INCINERATOR_BE = BLOCK_ENTITIES
			.register("item_incinerator_be", () -> BlockEntityType.Builder
					.of(ItemIncineratorBlockEntity::new, BlockRegistry.ITEM_INCINERATOR.get())
					.build(null));

	public static final RegistryObject<BlockEntityType<EntityRepulsorBlockEntity>> ENTITY_REPULSOR_BE = BLOCK_ENTITIES
			.register("entity_repulsor_be", () -> BlockEntityType.Builder
					.of(EntityRepulsorBlockEntity::new, BlockRegistry.ENTITY_REPULSOR.get())
					.build(null));
}
