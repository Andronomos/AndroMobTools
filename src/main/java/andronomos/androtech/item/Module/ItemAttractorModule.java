package andronomos.androtech.item.Module;

import andronomos.androtech.item.base.TickingDevice;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemAttractorModule extends TickingDevice {
	private final int range = 10;

	public ItemAttractorModule(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !isActivated(stack) || isBroken(stack) || !(entity instanceof Player)) return;
		ServerPlayer player = (ServerPlayer)entity;
		AABB area = player.getBoundingBox().inflate(range);
		attractItems(stack, level, player, area);
		attractExperience(stack, level, player, area);
	}

	private void attractItems(ItemStack stack, Level level, Player player, AABB area) {
		List<ItemEntity> itemsInRange = level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement") &&
						(item.getThrower() == null || !item.getThrower().equals(player.getUUID()) || !item.isPickable())
		);

		itemsInRange.forEach(item -> {
			if(!isBroken(stack)) {
				//boolean inserted = player.getInventory().add(item.getItem());

				//if(!inserted) {
				//	item.setPos(player.getX(), player.getY(), player.getZ());
				//}

				item.playerTouch(player);

				//if(takeDamage) {
				//	doDamage(stack, player, 1,true);
				//}
			}
		});
	}

	private void attractExperience(ItemStack stack, Level level, Player player, AABB area) {
		List<ExperienceOrb> experienceOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);

		experienceOrbs.forEach(orb -> {
			if(!isBroken(stack)) {
				player.takeXpDelay = 0;
				orb.playerTouch(player);
				//if(takeDamage) {
				//	doDamage(stack, player, 1,true);
				//}
			}
		});
	}
}
