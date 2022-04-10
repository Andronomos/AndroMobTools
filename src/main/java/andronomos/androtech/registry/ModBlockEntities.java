package andronomos.androtech.registry;

import andronomos.androtech.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<BlockEntityType<TestBlockBE>> TEST_BLOCK = BLOCK_ENTITIES.register("test_block_be", () -> BlockEntityType.Builder
            .of(TestBlockBE::new, ModBlocks.TEST_BLOCK.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<MobClonerBE>> MOB_CLONER_BE = BLOCK_ENTITIES.register("mob_cloner_be", () -> BlockEntityType.Builder
            .of(MobClonerBE::new, ModBlocks.MOB_CLONER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<LootAttractorBE>> LOOT_ATTRACTOR = BLOCK_ENTITIES.register("loot_attractor_be", () -> BlockEntityType.Builder
            .of(LootAttractorBE::new, ModBlocks.LOOT_ATTRACTOR.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<LootIncineratorBE>> LOOT_INCINERATOR = BLOCK_ENTITIES.register("loot_incinerator_be", () -> BlockEntityType.Builder
            .of(LootIncineratorBE::new, ModBlocks.LOOT_INCINERATOR.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<MobKillingPadBE>> MOB_KILLING_PAD = BLOCK_ENTITIES.register("mob_killing_pad_be", () -> BlockEntityType.Builder
            .of(MobKillingPadBE::new, ModBlocks.MOB_KILLING_PAD.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<CropHarvesterBE>> CROP_HARVESTER = BLOCK_ENTITIES.register("crop_harvester_be", () -> BlockEntityType.Builder
            .of(CropHarvesterBE::new, ModBlocks.CROP_HARVESTER.get())
            .build(null));
}
