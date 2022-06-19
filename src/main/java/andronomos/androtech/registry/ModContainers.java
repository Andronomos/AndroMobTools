package andronomos.androtech.registry;

import andronomos.androtech.block.ItemIncinerator.ItemIncineratorContainer;
import andronomos.androtech.block.amethystharvester.AmethystHarvester;
import andronomos.androtech.block.amethystharvester.AmethystHarvesterContainer;
import andronomos.androtech.block.animalfarmer.AnimalFarmerContainer;
import andronomos.androtech.block.cropfarmer.CropFarmerContainer;
import andronomos.androtech.block.itemattractor.ItemAttractorContainer;
import andronomos.androtech.block.itemmender.ItemMenderContainer;
import andronomos.androtech.block.mobcloner.MobClonerContainer;
import andronomos.androtech.block.pad.mobkillingpad.MobKillingPadContainer;
import andronomos.androtech.block.redstonetransmitter.RedstoneTransmitterContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<MenuType<MobClonerContainer>> MOB_CLONER = CONTAINERS.register("mob_cloner", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new MobClonerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<ItemAttractorContainer>> ITEM_ATTRACTOR = CONTAINERS.register("item_attractor", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new ItemAttractorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<ItemIncineratorContainer>> ITEM_INCINERATOR = CONTAINERS.register("item_incinerator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new ItemIncineratorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<CropFarmerContainer>> CROP_FARMER = CONTAINERS.register("crop_farmer", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new CropFarmerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<ItemMenderContainer>> ITEM_MENDER = CONTAINERS.register("item_mender", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new ItemMenderContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<MobKillingPadContainer>> MOB_KILLING_PAD = CONTAINERS.register("mob_killing_pad", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new MobKillingPadContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<RedstoneTransmitterContainer>> REDSTONE_TRANSMITTER = CONTAINERS.register("redstone_transmitter", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new RedstoneTransmitterContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<AnimalFarmerContainer>> ANIMAL_FARMER = CONTAINERS.register("animal_farmer", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new AnimalFarmerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<AnimalFarmerContainer>> AMETHYST_HARVESTER = CONTAINERS.register("amethyst_harvester", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new AmethystHarvesterContainer(windowId, pos, inv);
    }));
}
