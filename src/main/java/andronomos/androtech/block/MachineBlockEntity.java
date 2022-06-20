package andronomos.androtech.block;

import andronomos.androtech.Const;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
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

public abstract class MachineBlockEntity extends BlockEntity {
	public final ItemStackHandler inventoryItems = createInventoryItemHandler();
	public final LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventoryItems);

	public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	protected abstract ItemStackHandler createInventoryItemHandler();

	protected ItemStackHandler createInventoryItemHandler(int size) {
		return new ItemStackHandler(size) {
			@Override
			public int getSlotLimit(int slotId) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slotId, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

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
