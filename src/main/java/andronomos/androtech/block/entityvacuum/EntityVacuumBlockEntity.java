package andronomos.androtech.block.entityvacuum;

import andronomos.androtech.Constants;
import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.FluidRegistry;
import andronomos.androtech.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class EntityVacuumBlockEntity extends BaseBlockEntity implements MenuProvider {
	private static int RANGE = AndroTechConfig.ENTITY_VACUUM_RANGE.get();
	private static int XP_CAPACITY = AndroTechConfig.ENTITY_VACUUM_XP_CAPACITY.get();
	public boolean showRenderBox;
	private float xPos, yPos, zPos;
	private float xNeg, yNeg, zNeg;

	public EntityVacuumBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.ENTITY_VACUUM_BE.get(), pos, state, new SimpleContainerData(EntityVacuumBlock.SLOTS));
	}

	@Override
	public void onLoad() {
		xPos = RANGE;
		xNeg = RANGE;
		yPos = RANGE;
		yNeg = RANGE;
		zPos = RANGE;
		zNeg = RANGE;
		super.onLoad();
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.putBoolean("showRenderBox", showRenderBox);
		tag.putFloat("xPos", xPos);
		tag.putFloat("yPos", yPos);
		tag.putFloat("zPos", zPos);
		tag.putFloat("xNeg", xNeg);
		tag.putFloat("yNeg", yNeg);
		tag.putFloat("zNeg", zNeg);
		super.saveAdditional(tag);
	}

	@OnlyIn(Dist.CLIENT)
	public AABB getAABBForRender() {
		return new AABB(- xNeg, - yNeg, - zNeg, 1D + xPos, 1D + yPos, 1D + zPos);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox() {
		return getWorkArea();
	}

	@Override
	public AABB getWorkArea() {
		return new AABB(getBlockPos().getX() - xNeg, getBlockPos().getY() - yNeg, getBlockPos().getZ() - zNeg, getBlockPos().getX() + 1D + xPos, getBlockPos().getY() + 1D + yPos, getBlockPos().getZ() + 1D + zPos);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		showRenderBox = tag.getBoolean("showRenderBox");
		xPos = tag.getFloat("xPos");
		yPos = tag.getFloat("yPos");
		zPos = tag.getFloat("zPos");
		xNeg = tag.getFloat("xNeg");
		yNeg = tag.getFloat("yNeg");
		zNeg = tag.getFloat("zNeg");
		super.load(tag);
	}

	@Nonnull
	public ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Constants.VANILLA_INVENTORY_SLOT_COUNT) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public FluidTank createFluidHandler() {
		return new FluidTank(XP_CAPACITY * 16) {
			@Override
			protected void onContentsChanged() {
				setChanged();
			}

			@Override
			public boolean isFluidValid(FluidStack stack) {
				return stack.getFluid() == FluidRegistry.LIQUID_XP.get();
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(EntityVacuumBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
		return new EntityVacuumMenu(containerId, playerInventory, this, this.data);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		if(!state.getValue(POWERED)) return;

		if (level.getGameTime() % 3 == 0 && level.getBlockState(getBlockPos()).getBlock() instanceof EntityVacuumBlock) {
			if(!InventoryHelper.isFull(itemHandler)) {
				captureDroppedItems(level);
			}

			prevTankAmount = fluidHandler.getFluidAmount();

			if(fluidHandler.isEmpty() || fluidHandler.getFluid().containsFluid(new FluidStack(FluidRegistry.LIQUID_XP.get(), 1))) {
				CaptureExperience(level);
			}

			if(prevTankAmount != fluidHandler.getFluidAmount()) {
				sendUpdate();
			}

			setChanged(level, pos, state);
		}
	}

	private void captureDroppedItems(ServerLevel level) {
		for(ItemEntity item : EntityHelper.getNearbyItems(level, getWorkArea())) {
			if(item == null) {
				return;
			}

			ItemStack stack = InventoryHelper.insert(item.getItem().copy(), itemHandler, false);

			if (!stack.isEmpty()) {
				item.setItem(stack);
			} else {
				item.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	private void CaptureExperience(ServerLevel level) {
		for(ExperienceOrb orb : EntityHelper.getNearbyExperience(level, getWorkArea())) {
			int xpAmount = orb.getValue();

			if(fluidHandler.getFluidAmount() < fluidHandler.getCapacity() - xpAmount * 20) {
				fluidHandler.fill(new FluidStack(FluidRegistry.LIQUID_XP.get(), xpAmount * 20), IFluidHandler.FluidAction.EXECUTE);
				orb.value = 0;
				orb.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	private List<ItemEntity> getNearbyItems() {
		return Objects.requireNonNull(getLevel()).getEntitiesOfClass(ItemEntity.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private List<ExperienceOrb> getNearbyExperience() {
		return Objects.requireNonNull(getLevel()).getEntitiesOfClass(ExperienceOrb.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	public void toggleRenderBox() {
		showRenderBox = !showRenderBox;
		setChanged();
	}
}
