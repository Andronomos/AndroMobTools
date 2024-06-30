package andronomos.androtech.item;

import andronomos.androtech.Constants;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.TickingItem;
import andronomos.androtech.util.EntityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class PortableEntityVacuumItem extends TickingItem {
	private final int range = AndroTechConfig.PORTABLE_ENTITY_VACUUM_RANGE.get();

	public PortableEntityVacuumItem(Properties properties) {
		super(properties, AndroTechConfig.PORTABLE_ENTITY_VACUUM_TAKE_DAMAGE.get());
	}

	@Override
	public int getTickDelay() {
		return Constants.TicksInSeconds.ONE;
	}

	@Override
	public void onTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		ServerPlayer player = (ServerPlayer)entity;
		AABB area = player.getBoundingBox().inflate(range);
		vacuumItems(stack, level, player, area);
		vacuumExperience(stack, level, player, area);
	}

	private void vacuumItems(ItemStack stack, Level level, ServerPlayer player, AABB area) {
		EntityHelper.getNearbyItems(level, area, player).forEach(item -> {
			if(!isBroken(stack)) {
				item.setNoPickUpDelay();
				item.playerTouch(player);
				if(hasDurability) {
					doDamage(stack, player, AndroTechConfig.PORTABLE_ENTITY_VACUUM_DAMAGE_RATE.get());
				}
			}
		});
	}

	private void attractExperience(ItemStack stack, Level level, ServerPlayer player, AABB area) {
		List<ExperienceOrb> experienceOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);
		experienceOrbs.forEach(orb -> {
			if(!isBroken(stack)) {
				player.takeXpDelay = 0;
				orb.playerTouch(player);
			}
		});
	}
}
