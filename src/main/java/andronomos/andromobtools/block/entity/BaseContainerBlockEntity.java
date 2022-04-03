package andronomos.andromobtools.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public abstract class BaseContainerBlockEntity extends BlockEntity {
	public final ItemStackHandler inputItems = createItemHandler();
	public final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> inputItems);

	public BaseContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		itemHandler.invalidate();
	}

	protected abstract ItemStackHandler createItemHandler();

	//@Override
	//public void load(CompoundTag tag) {
	//	super.load(tag);
	//}
	//
	//@Override
	//protected void saveAdditional(CompoundTag tag) {
	//	super.saveAdditional(tag);
	//}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null)
			itemHandler.invalidate();
	}
}
