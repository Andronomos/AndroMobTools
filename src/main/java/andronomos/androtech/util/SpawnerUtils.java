package andronomos.androtech.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;

public class SpawnerUtils {
	public static String getEntityString(BaseSpawner spawner) {
		// Get entity ResourceLocation string from spawner by creating a empty compound which we make our
		// spawner logic write to. We can then access what type of entity id the spawner has inside
		CompoundTag tempTag = new CompoundTag();
		tempTag = spawner.save(tempTag);
		String entityString = tempTag.get("SpawnData").toString();

		// Strips the string
		// Example: {id: "minecraft:xxx_xx"} --> minecraft:xxx_xx
		entityString = entityString.substring(entityString.indexOf("\"") + 1);
		entityString = entityString.substring(0, entityString.indexOf("\""));

		// Leave if the spawner does not contain an entity
		if(entityString.equalsIgnoreCase(EntityType.AREA_EFFECT_CLOUD.getRegistryName().toString()))
			return "";

		return entityString;
	}
}
