package andronomos.androtech.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class EntityHelper {
	public static List<ItemEntity> getNearbyItems(Level level, AABB area, ServerPlayer player) {
		return level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement")
						&& (item.getOwner() == null || !item.getOwner().getUUID().equals(player.getUUID()) || !item.isPickable())
						&& item.isAlive()
		);
	}

	public static List<ItemEntity> getNearbyItems(Level level, AABB area) {
		return level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement")
						&& (item.getOwner() == null || !item.isPickable())
						&& item.isAlive()
		);
	}

	public static List<ExperienceOrb> getNearbyExperience(Level level, AABB area) {
		return Objects.requireNonNull(level).getEntitiesOfClass(ExperienceOrb.class, area, EntitySelector.ENTITY_STILL_ALIVE);
	}
}
