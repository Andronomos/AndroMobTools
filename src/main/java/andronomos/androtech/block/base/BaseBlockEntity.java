package andronomos.androtech.block.base;

import andronomos.androtech.util.BoundingBoxHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class BaseBlockEntity extends BlockEntity {
	protected final ItemStackHandler itemHandler = createInventoryItemHandler();
	protected final FluidTank fluidHandler = createFluidHandler();
	protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	protected LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
	protected final ContainerData data;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public int prevTankAmount;

	public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ContainerData data) {
		super(type, pos, state);
		this.data = data;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == ForgeCapabilities.ITEM_HANDLER && itemHandler != null) {
			return lazyItemHandler.cast();
		}

		if(cap == ForgeCapabilities.FLUID_HANDLER && fluidHandler != null) {
			return lazyFluidHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		if (itemHandler != null) {
			lazyItemHandler = LazyOptional.of(() -> itemHandler);
		}

		if(fluidHandler != null) {
			lazyFluidHandler = LazyOptional.of(() -> fluidHandler);
		}

		super.onLoad();
	}

	@Override
	public void invalidateCaps() {
		if (itemHandler != null) {
			lazyItemHandler.invalidate();
		}

		if(fluidHandler != null) {
			lazyFluidHandler.invalidate();
		}

		super.invalidateCaps();
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		if (itemHandler != null) {
			tag.put("Inventory", itemHandler.serializeNBT());
		}

		if (fluidHandler != null) {
			fluidHandler.writeToNBT(tag);
		}

		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		if (itemHandler != null) {
			itemHandler.deserializeNBT(tag.getCompound("Inventory"));
		}

		if (fluidHandler != null) {
			fluidHandler.readFromNBT(tag);
		}

		super.load(tag);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag nbt = super.getUpdateTag();
		saveAdditional(nbt);
		return nbt;
	}

	public ItemStackHandler createInventoryItemHandler() {
		return null;
	}

	public FluidTank createFluidHandler() {
		return null;
	}

	protected void clientTick(Level level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) { }

	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) { }

	protected AABB getWorkArea() {
		return BoundingBoxHelper.boxFromCenter(this.worldPosition, 0);
	}

	public void setFluid(FluidStack stack) {
		if(fluidHandler != null) {
			this.fluidHandler.setFluid(stack);
		}
	}

	public FluidStack getFluidStack() {
		if(fluidHandler != null) {
			return this.fluidHandler.getFluid();
		}

		return null;
	}

	public void sendUpdate() {
		Objects.requireNonNull(getLevel()).sendBlockUpdated(worldPosition, getLevel().getBlockState(worldPosition), getLevel().getBlockState(worldPosition), Block.UPDATE_ALL);
	}
}
