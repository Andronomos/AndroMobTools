package andronomos.androtech.block.damagepad;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.util.RadiusHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamagePadBlockEntity extends BaseBlockEntity implements MenuProvider {
	public DamagePadBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.DAMAGE_PAD_BE.get(), pos, state, new SimpleContainerData(DamagePadBlock.PAD_SLOTS));
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(DamagePadBlock.PAD_SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
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
		return new DamagePadMenu(containerId, inventory, this, this.data);
	}

	@Override
	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) {
		if (level.getGameTime() % 10 == 0 && level.getBlockState(getBlockPos()).getBlock() instanceof DamagePadBlock) {
			activate();
		}
	}

	private void activate() {
		List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, getWorkArea());

		for (LivingEntity entity : list) {
			if (entity == null || entity.isCrouching()) {
				continue;
			}

			ItemStack sword = new ItemStack(ItemRegistry.FAKE_SWORD.get(), 1);

			if (hasSharpnessUpgrade())
				sword.enchant(Enchantments.SHARPNESS, itemHandler.getStackInSlot(0).getCount() * 10);
			if (hasLootingUpgrade())
				sword.enchant(Enchantments.MOB_LOOTING, itemHandler.getStackInSlot(1).getCount());
			if (hasFlameUpgrade())
				sword.enchant(Enchantments.FIRE_ASPECT, itemHandler.getStackInSlot(2).getCount());
			if (hasSmiteUpgrade())
				sword.enchant(Enchantments.SMITE, itemHandler.getStackInSlot(3).getCount() * 10);

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
		return RadiusHelper.oneByThreeByOneFromTop(getBlockPos());
	}

	private boolean hasSharpnessUpgrade() {
		return itemHandler.getStackInSlot(0).getItem() == ItemRegistry.SHARPNESS_AUGMENT.get();
	}

	private boolean hasLootingUpgrade() {
		return itemHandler.getStackInSlot(1).getItem() == ItemRegistry.LOOTING_AUGMENT.get();
	}

	private boolean hasFlameUpgrade() {
		return itemHandler.getStackInSlot(2).getItem() == ItemRegistry.FIRE_AUGMENT.get();
	}

	private boolean hasSmiteUpgrade() {
		return itemHandler.getStackInSlot(3).getItem() == ItemRegistry.SMITE_AUGMENT.get();
	}
}