package andronomos.androtech.item;

import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemAttractorModuleItem extends AbstractActivatableItem {
	public static final int ATTRACTOR_UNIT_DURABILITY = 1000;
	private final int pickupRange = 10;

	public ItemAttractorModuleItem(Properties properties, boolean takeDamage, boolean isRepairable) {
		super(properties, takeDamage, isRepairable);
	}

	public ItemAttractorModuleItem(Properties properties) {
		super(properties, false, false);
	}

	@Override
	public InteractionResultHolder use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(!isActivated(stack) && !isBroken(stack)) {
				activate(stack, player);
			} else {
				deactivate(stack, player);
			}
		}

		return InteractionResultHolder.success(stack);
	}

	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !isActivated(stack) || isBroken(stack) || !(entity instanceof Player)) return;

		Player player = (Player)entity;

		AABB area = new AABB(player.position().add(-pickupRange, -pickupRange, -pickupRange), player.position().add(pickupRange, pickupRange, pickupRange));

		List<ItemEntity> itemsInRange = level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement") &&
						(item.getThrower() == null || !item.getThrower().equals(player.getUUID()) || !item.isPickable())
		);

		itemsInRange.forEach(item -> {
			if(!isBroken(stack)) {
				if(player.getInventory().add(item.getItem())) {
					if(takeDamage) {
						doDamage(stack, player, 1,true);
					}
				}

				//item.setPos(player.getX(), player.getY(), player.getZ());
				//if(this.takeDamage) {
				//	doDamage(stack, player);
				//}
			}
		});

		List<ExperienceOrb> experienceOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);

		experienceOrbs.forEach(orb -> {
			if(!isBroken(stack)) {
				player.takeXpDelay = 0;
				orb.playerTouch(player);
				if(takeDamage) {
					doDamage(stack, player, 1,true);
				}
			}
		});
	}
}
