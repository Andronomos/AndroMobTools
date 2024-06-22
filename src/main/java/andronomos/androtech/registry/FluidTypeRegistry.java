package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class FluidTypeRegistry {
	public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
	public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
	public static final ResourceLocation XP_OVERLAY_RL = new ResourceLocation(AndroTech.MODID, "fluids/in_liquid_xp");

	public static DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AndroTech.MODID);

	public static final RegistryObject<FluidType> LIQUID_XP_FLUID_TYPE = register("liquid_xp_fluid",
			FluidType.Properties.create()
					.lightLevel(10)
					.canSwim(true)
					.canDrown(false)
					.sound(SoundActions.BUCKET_EMPTY, SoundEvents.EXPERIENCE_ORB_PICKUP)
					.sound(SoundActions.BUCKET_FILL, SoundEvents.PLAYER_LEVELUP)
	);

	private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
		return FLUID_TYPES.register(name, () -> new BaseFluidType(properties, WATER_STILL_RL, WATER_FLOWING_RL, XP_OVERLAY_RL, 0xA1E038D0,
				new Vector3f(224f / 255f, 56f /255f, 208f / 255f)));
	}
}
