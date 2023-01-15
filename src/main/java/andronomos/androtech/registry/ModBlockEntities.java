package andronomos.androtech.registry;

import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGenerator;
import andronomos.androtech.block.machine.creativeenergygenerator.CreativeEnergyGeneratorBlockEntity;
import andronomos.androtech.block.machine.itemmender.ItemMenderBlockEntity;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerBlockEntity;
import andronomos.androtech.block.machine.mobcloner.MobClonerBlockEntity;
import andronomos.androtech.block.machine.itemattractor.ItemAttractorBlockEntity;
import andronomos.androtech.block.machine.itemincinerator.ItemIncineratorBlockEntity;
import andronomos.androtech.block.machine.redstonetransmitter.RedstoneTransmitterBlockEntity;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, andronomos.androtech.AndroTech.MOD_ID);

	public static final RegistryObject<BlockEntityType<CropFarmerBlockEntity>> CROP_FARMER = BLOCK_ENTITIES.register("crop_farmer",
			() -> BlockEntityType.Builder.of(CropFarmerBlockEntity::new, ModBlocks.CROP_FARMER.get()).build(null));

	public static final RegistryObject<BlockEntityType<MobClonerBlockEntity>> MOB_CLONER = BLOCK_ENTITIES.register("mob_cloner", () -> BlockEntityType.Builder
			.of(MobClonerBlockEntity::new, ModBlocks.MOB_CLONER.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<ItemAttractorBlockEntity>> ITEM_ATTRACTOR = BLOCK_ENTITIES.register("item_attractor", () -> BlockEntityType.Builder
			.of(ItemAttractorBlockEntity::new, ModBlocks.ITEM_ATTRACTOR.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<ItemIncineratorBlockEntity>> ITEM_INCENERATOR = BLOCK_ENTITIES.register("item_incinerator", () -> BlockEntityType.Builder
			.of(ItemIncineratorBlockEntity::new, ModBlocks.ITEM_INCINERATOR.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<RedstoneTransmitterBlockEntity>> REDSTONE_TRANSMITTER = BLOCK_ENTITIES.register("redstone_transmitter", () -> BlockEntityType.Builder
			.of(RedstoneTransmitterBlockEntity::new, ModBlocks.REDSTONE_TRANSMITTER.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<MobKillingPadBlockEntity>> MOB_KILLING_PAD = BLOCK_ENTITIES.register("mob_killing_pad", () -> BlockEntityType.Builder
			.of(MobKillingPadBlockEntity::new, ModBlocks.MOB_KILLING_PAD.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<ItemMenderBlockEntity>> ITEM_MENDER = BLOCK_ENTITIES.register("item_mender", () -> BlockEntityType.Builder
			.of(ItemMenderBlockEntity::new, ModBlocks.ITEM_MENDER.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<CreativeEnergyGeneratorBlockEntity>> CREATIVE_ENERGY_GENERATOR = BLOCK_ENTITIES.register("creative_energy_generator", () -> BlockEntityType.Builder
			.of(CreativeEnergyGeneratorBlockEntity::new, ModBlocks.CREATIVE_ENERGY_GENERATOR.get())
			.build(null));
}
