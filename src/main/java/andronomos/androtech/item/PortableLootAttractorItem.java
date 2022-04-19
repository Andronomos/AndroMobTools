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

public class PortableLootAttractorItem extends AbstractActivatableItem {
	private final int pickupRange = 10;

	public PortableLootAttractorItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getDamage(ItemStack stack) {
		return 115200; //should last for ~50 inventories worth of item stacks
	}

	@Override
	public InteractionResultHolder use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if(!isActivated(stack) && canUse(stack)) {
			this.setState(stack, 1);
		} else {
			this.setState(stack, 0);
		}

		return InteractionResultHolder.success(stack);
	}

	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (!isActivated(stack) || !canUse(stack) || !(entity instanceof Player)) return;

		Player player = (Player)entity;

		AABB area = new AABB(player.position().add(-pickupRange, -pickupRange, -pickupRange), player.position().add(pickupRange, pickupRange, pickupRange));

		List<ItemEntity> itemsInRange = level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement") &&
						(item.getThrower() == null || !item.getThrower().equals(player.getUUID()) || !item.isPickable())
		);

		itemsInRange.forEach(item -> {
			item.setPos(player.getX(), player.getY(), player.getZ());
			ItemStackUtil.damageItem(player, stack, 1);
		});

		if (!level.isClientSide()){
			List<ExperienceOrb> experienceOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);

			experienceOrbs.forEach(orb -> {
				player.takeXpDelay = 0;
				orb.playerTouch(player);
			});
		}
	}

}
