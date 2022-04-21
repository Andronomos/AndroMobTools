package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.loot.WitherSkeletonLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLoot {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, AndroTech.MOD_ID);

    public static final RegistryObject<WitherSkeletonLootModifier.Serializer> GLM_WITHER_KILL = LOOT_MODIFIERS.register("wither_skeleton", WitherSkeletonLootModifier.Serializer::new);
}
