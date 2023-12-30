package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamagePadBlockEntity extends BaseBlockEntity implements MenuProvider {
	public DamagePadBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.DAMAGE_PAD_BE.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(DamagePadBlock.PAD_SLOTS) {
			@Override
			public int getSlotLimit(int slot) {
				return DamagePadBlock.UPGRADE_STACK_LIMIT;
			}

			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				//todo: check for upgrade item
				return super.isItemValid(slot, stack);
			}
		};
	}


	@Override
	public Component getDisplayName() {
		return Component.translatable(DamagePadBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new DamagePadMenu(containerId, inventory, this);
	}

	public void serverTick() {
		if (level.getGameTime() % 10 == 0 && level.getBlockState(getBlockPos()).getBlock() instanceof DamagePadBlock) {
			activate();
		}
	}

	private void activate() {
		List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, getWorkArea());

		for (int i = 0; i < list.size(); i++) {
			LivingEntity entity = list.get(i);

			if (entity == null || entity.isCrouching()) {
				continue;
			}

			ItemStack sword = new ItemStack(ItemRegistry.FAKE_SWORD.get(), 1);

			//todo: get upgrades and apply them to the sword
			//sword.enchant(Enchantments.SHARPNESS, 1 * 10);

			FakePlayer fp = FakePlayerFactory.get((ServerLevel) getLevel(), AndroTech.PROFILE);
			fp.setItemInHand(InteractionHand.MAIN_HAND, sword);
			fp.setSilent(true);
			fp.attack(entity);
			fp.resetAttackStrengthTicker();
			fp.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			entity.setLastHurtByMob(null);
			sword.setDamageValue(0);

			AndroTech.LOGGER.info(String.format("DamagePadBlockEntity#activate | entity health: %s", entity.getHealth()));
		}
	}

	public AABB getWorkArea() {
		return RadiusUtils.oneByThreeByOneFromTop(getBlockPos());
	}
}
