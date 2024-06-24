package andronomos.androtech.block.mobkiller;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.util.BoundingBoxHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class MobKillerBlockEntity extends BaseBlockEntity implements MenuProvider {
	public boolean showRenderBox;
	private float xPos, yPos, zPos;
	private float xNeg, yNeg, zNeg;

	public MobKillerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.MOB_KILLER_BE.get(), pos, state, new SimpleContainerData(MobKillerBlock.SLOTS));
	}

	@Override
	public ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(MobKillerBlock.SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(MobKillerBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new MobKillerMenu(containerId, inventory, this, this.data);
	}

	@Override
	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) {
		if (blockEntity instanceof MobKillerBlockEntity) {
			BlockState stateAtPos = level.getBlockState(pos);

			if (level.getGameTime() % 5 == 0 && state.getBlock() instanceof MobKillerBlock) {
				if (stateAtPos.getValue(MobKillerBlock.POWERED)) {
					activate();
				}
			}

			if (!level.isClientSide) {
				setAABB();
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public AABB getAABBForRender() {
		return new AABB(- xNeg, - yNeg, - zNeg, 1D + xPos, 1D + yPos, 1D + zPos);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		showRenderBox = tag.getBoolean("showRenderBox");
		xPos = tag.getFloat("xPos");
		yPos = tag.getFloat("yPos");
		zPos = tag.getFloat("zPos");
		xNeg = tag.getFloat("xNeg");
		yNeg = tag.getFloat("yNeg");
		zNeg = tag.getFloat("zNeg");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putBoolean("showRenderBox", showRenderBox);
		tag.putFloat("xPos", xPos);
		tag.putFloat("yPos", yPos);
		tag.putFloat("zPos", zPos);
		tag.putFloat("xNeg", xNeg);
		tag.putFloat("yNeg", yNeg);
		tag.putFloat("zNeg", zNeg);
	}

	@Nonnull
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = new CompoundTag();
		saveAdditional(nbt);
		return nbt;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		CompoundTag nbt = new CompoundTag();
		saveAdditional(nbt);
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
		load(Objects.requireNonNull(packet.getTag()));
		onContentsChanged();
	}

	public void onContentsChanged() {
		if (!Objects.requireNonNull(getLevel()).isClientSide) {
			final BlockState state = getLevel().getBlockState(getBlockPos());
			setAABB();
			getLevel().sendBlockUpdated(getBlockPos(), state, state, 8);
			setChanged();
		}
	}

	private void activate() {
		Level level = getLevel();

		if(level == null) {
			return;
		}

		List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, getAABB());

		for (LivingEntity entity : list) {
			if (entity == null || entity.isCrouching()) {
				continue;
			}

			ItemStack sword = new ItemStack(ItemRegistry.FAKE_SWORD.get(), 1);

			if (hasSharpnessUpgrade())
				sword.enchant(Enchantments.SHARPNESS, itemHandler.getStackInSlot(0).getCount());
			if (hasLootingUpgrade())
				sword.enchant(Enchantments.MOB_LOOTING, itemHandler.getStackInSlot(1).getCount());
			if (hasFireUpgrade())
				sword.enchant(Enchantments.FIRE_ASPECT, itemHandler.getStackInSlot(2).getCount());
			//if (hasSmiteUpgrade())
			//	sword.enchant(Enchantments.SMITE, itemHandler.getStackInSlot(3).getCount());

			FakePlayer fp = FakePlayerFactory.get((ServerLevel) getLevel(), AndroTech.PROFILE);
			fp.setItemInHand(InteractionHand.MAIN_HAND, sword);
			fp.setSilent(true);
			fp.attack(entity);
			fp.resetAttackStrengthTicker();
			fp.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			entity.setLastHurtByMob(null);
			sword.setDamageValue(0);
		}
	}

	public AABB getWorkArea() {
		return BoundingBoxHelper.threeWideThreeTallFromTop(getBlockPos());
	}

	private void setAABB() {
		BlockState state = Objects.requireNonNull(getLevel()).getBlockState(getBlockPos());

		if (!(state.getBlock() instanceof MobKillerBlock)) {
			return;
		}

		Direction facing = state.getValue(MobKillerBlock.FACING);

		if (facing == Direction.UP) {
			yPos = 3;
			yNeg = -1;
			xPos = 1;
			xNeg = 1;
			zPos = 1;
			zNeg = 1;
		}

		if (facing == Direction.DOWN) {
			yNeg = 3;
			yPos = -1;
			xPos = 1;
			xNeg = 1;
			zPos = 1;
			zNeg = 1;
		}

		if (facing == Direction.WEST) {
			xNeg = 3;
			xPos = -1;
			zPos = 1;
			zNeg = 1;
			yPos = 2;
			yNeg = 0;
		}

		if (facing == Direction.EAST) {
			xPos = 3;
			xNeg = -1;
			zPos = 1;
			zNeg = 1;
			yPos = 2;
			yNeg = 0;
		}

		if (facing == Direction.NORTH) {
			zNeg = 3;
			zPos = -1;
			xPos = 1;
			xNeg = 1;
			yPos = 2;
			yNeg = 0;
		}

		if (facing == Direction.SOUTH) {
			zPos = 3;
			zNeg = -1;
			xPos = 1;
			xNeg = 1;
			yPos = 2;
			yNeg = 0;
		}

		getLevel().sendBlockUpdated(getBlockPos(), state, state, 8);
	}

	public AABB getAABB() {
		return new AABB(getBlockPos().getX() - xNeg, getBlockPos().getY() - yNeg, getBlockPos().getZ() - zNeg, getBlockPos().getX() + 1D + xPos, getBlockPos().getY() + 1D + yPos, getBlockPos().getZ() + 1D + zPos);
	}

	private boolean hasSharpnessUpgrade() {
		return itemHandler.getStackInSlot(0).getItem() == ItemRegistry.SHARPNESS_AUGMENT.get();
	}

	private boolean hasLootingUpgrade() {
		return itemHandler.getStackInSlot(1).getItem() == ItemRegistry.LOOTING_AUGMENT.get();
	}

	private boolean hasFireUpgrade() {
		return itemHandler.getStackInSlot(2).getItem() == ItemRegistry.FIRE_AUGMENT.get();
	}

	public void toggleRenderBox() {
		showRenderBox = !showRenderBox;
		setChanged();
	}
}
