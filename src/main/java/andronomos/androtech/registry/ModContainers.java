package andronomos.androtech.registry;

import andronomos.androtech.inventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<MenuType<CropFarmerContainer>> CROP_FARMER = CONTAINERS.register("crop_farmer", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new CropFarmerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<ItemIncineratorContainer>> ITEM_INCINERATOR = CONTAINERS.register("item_incinerator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new ItemIncineratorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<ItemAttractorContainer>> ITEM_ATTRACTOR = CONTAINERS.register("item_attractor", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new ItemAttractorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<MobClonerContainer>> MOB_CLONER = CONTAINERS.register("mob_cloner", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MobClonerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<RedstoneTransmitterContainer>> REDSTONE_TRANSMITTER = CONTAINERS.register("redstone_transmitter", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new RedstoneTransmitterContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<MobKillingPadContainer>> MOB_KILLING_PAD = CONTAINERS.register("mob_killing_pad", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MobKillingPadContainer(windowId, pos, inv);
    }));

    //public static final RegistryObject<MenuType<BackpackContainer>> BACKPACK = CONTAINERS.register("backpack", () -> IForgeMenuType.create((windowId, inv, data) -> {
    //    Level level = inv.player.getCommandSenderWorld();
    //    return new BackpackContainer(windowId, inv);
    //}));
    //
    //public static final RegistryObject<MenuType<TestBlockContainer>> TEST_BLOCK = CONTAINERS.register("test_block", () -> IForgeMenuType.create((windowId, inv, data) -> {
    //    BlockPos pos = data.readBlockPos();
    //    Level level = inv.player.getCommandSenderWorld();
    //    return new TestBlockContainer(windowId, pos, inv);
    //}));
}
