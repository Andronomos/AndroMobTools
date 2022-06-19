package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseContainerBE extends BlockEntity {
	public final ItemStackHandler inventoryItems = createInventoryItemHandler();
	public final LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventoryItems);

	public BaseContainerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	protected abstract ItemStackHandler createInventoryItemHandler();

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return inventoryHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (inventoryHandler != null)
			inventoryHandler.invalidate();
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		tag.put("Inventory", inventoryItems.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Inventory")) {
			inventoryItems.deserializeNBT(tag.getCompound("Inventory"));
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		inventoryHandler.invalidate();
	}
}
