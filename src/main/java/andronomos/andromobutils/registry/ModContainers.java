package andronomos.andromobutils.registry;

import andronomos.andromobutils.AndroMobUtils;
import andronomos.andromobutils.inventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, AndroMobUtils.MOD_ID);

    public static final RegistryObject<MenuType<GoldenSpawnerContainer>> MOB_CLONER = CONTAINERS.register("mob_cloner", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new GoldenSpawnerContainer(windowId, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<LootAttractorContainer>> LOOT_ATTRACTOR = CONTAINERS.register("loot_attractor", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new LootAttractorContainer(windowId, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<LootIncineratorContainer>> LOOT_INCINERATOR = CONTAINERS.register("loot_incinerator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new LootIncineratorContainer(windowId, pos, inv, inv.player);
    }));

    public static final RegistryObject<MenuType<MobKillingPadContainer>> MOB_KILLING_PAD = CONTAINERS.register("mob_killing_pad", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MobKillingPadContainer(windowId, pos, inv, inv.player);
    }));

}
