package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.pad.damagepad.DamagePadBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AndroTech.MODID);

	public static final RegistryObject<BlockEntityType<DamagePadBlockEntity>> DAMAGE_PAD = BLOCK_ENTITIES
			.register("damage_pad_block_entity", () -> BlockEntityType.Builder
			.of(DamagePadBlockEntity::new, BlockRegistry.DAMAGE_PAD.get())
			.build(null));
}
