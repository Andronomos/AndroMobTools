package andronomos.androtech.registry;

import andronomos.androtech.block.ItemIncinerator.ItemIncineratorBE;
import andronomos.androtech.block.amethystharvester.AmethystHarvesterBE;
import andronomos.androtech.block.animalfarmer.AnimalFarmerBE;
import andronomos.androtech.block.cropfarmer.CropFarmerBE;
import andronomos.androtech.block.itemattractor.ItemAttractorBE;
import andronomos.androtech.block.itemmender.ItemMenderBE;
import andronomos.androtech.block.mobcloner.MobClonerBE;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadBE;
import andronomos.androtech.block.redstonetransmitter.RedstoneTransmitterBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<BlockEntityType<MobClonerBE>> MOB_CLONER_BE = BLOCK_ENTITIES.register("mob_cloner_be", () -> BlockEntityType.Builder
            .of(MobClonerBE::new, ModBlocks.MOB_CLONER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<ItemAttractorBE>> ITEM_ATTRACTOR = BLOCK_ENTITIES.register("item_attractor_be", () -> BlockEntityType.Builder
            .of(ItemAttractorBE::new, ModBlocks.ITEM_ATTRACTOR.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<ItemIncineratorBE>> ITEM_INCINERATOR = BLOCK_ENTITIES.register("item_incinerator_be", () -> BlockEntityType.Builder
            .of(ItemIncineratorBE::new, ModBlocks.ITEM_INCINERATOR.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<CropFarmerBE>> CROP_FARMER = BLOCK_ENTITIES.register("crop_farmer_be", () -> BlockEntityType.Builder
            .of(CropFarmerBE::new, ModBlocks.CROP_FARMER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<ItemMenderBE>> ITEM_MENDER = BLOCK_ENTITIES.register("item_mender_be", () -> BlockEntityType.Builder
            .of(ItemMenderBE::new, ModBlocks.ITEM_MENDER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<MobKillingPadBE>> MOB_KILLING_PAD = BLOCK_ENTITIES.register("mob_killing_pad_be", () -> BlockEntityType.Builder
            .of(MobKillingPadBE::new, ModBlocks.MOB_KILLING_PAD.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<RedstoneTransmitterBE>> REDSTONE_TRANSMITTER = BLOCK_ENTITIES.register("redstone_transmitter_be", () -> BlockEntityType.Builder
            .of(RedstoneTransmitterBE::new, ModBlocks.REDSTONE_TRANSMITTER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<AnimalFarmerBE>> ANIMAL_FARMER = BLOCK_ENTITIES.register("animal_farmer_be", () -> BlockEntityType.Builder
            .of(AnimalFarmerBE::new, ModBlocks.ANIMAL_FARMER.get())
            .build(null));

    public static final RegistryObject<BlockEntityType<AmethystHarvesterBE>> AMETHYST_HARVESTER = BLOCK_ENTITIES.register("amethyst_harvester_be", () -> BlockEntityType.Builder
            .of(AmethystHarvesterBE::new, ModBlocks.AMETHYST_HARVESTER.get())
            .build(null));
}
