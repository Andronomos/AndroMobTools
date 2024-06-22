package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidRegistry {
	public static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AndroTech.MODID);

	public static final RegistryObject<FlowingFluid> LIQUID_XP_SOURCE = FLUIDS.register("liquid_xp_fluid",
			() -> new ForgeFlowingFluid.Source(FluidRegistry.LIQUID_XP_FLUID_PROPERTIES));

	public static final RegistryObject<FlowingFluid> LIQUID_XP_FLOWING = FLUIDS.register("flowing_liquid_xp",
			() -> new ForgeFlowingFluid.Flowing(FluidRegistry.LIQUID_XP_FLUID_PROPERTIES));

	public static final ForgeFlowingFluid.Properties LIQUID_XP_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
			FluidTypeRegistry.LIQUID_XP_FLUID_TYPE, LIQUID_XP_SOURCE, LIQUID_XP_FLOWING)
			.slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockRegistry.LIQUID_XP_BLOCK)
			.bucket(ItemRegistry.LIQUID_XP_BUCKET);
}
