package andronomos.androtech.block.base;

import andronomos.androtech.util.BoundingBoxHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBlockEntity extends BlockEntity {
	protected final ItemStackHandler itemHandler = createInventoryItemHandler();
	protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	protected final ContainerData data;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ContainerData data) {
		super(type, pos, state);
		this.data = data;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == ForgeCapabilities.ITEM_HANDLER && itemHandler != null) {
			return lazyItemHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null) {
			lazyItemHandler.invalidate();
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		if (itemHandler != null) {
			tag.put("Inventory", itemHandler.serializeNBT());
		}
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		if (itemHandler != null) {
			itemHandler.deserializeNBT(tag.getCompound("Inventory"));
		}
		super.load(tag);
	}

	protected abstract ItemStackHandler createInventoryItemHandler();

	protected void clientTick(Level level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) { }

	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) { }

	protected AABB getWorkArea() {
		return BoundingBoxHelper.boxFromCenter(this.worldPosition, 0);
	}
}
