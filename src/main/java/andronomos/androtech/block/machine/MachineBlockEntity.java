package andronomos.androtech.block.machine;

import andronomos.androtech.Const;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerBlockEntity;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MachineBlockEntity extends BlockEntity {
	public final ItemStackHandler itemHandler = createInventoryItemHandler();
	public final ModEnergyStorage energyHandler  = createEnergyHandler();
	public LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	public LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
	public static int energyRequired = 32;
	public int tickDelay = Const.TicksInSeconds.FIVE;
	public int tickCounter = 0;

	public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return lazyItemHandler.cast();
		}

		if(cap == ForgeCapabilities.ENERGY) {
			return lazyEnergyHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
		lazyEnergyHandler = LazyOptional.of(() -> energyHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
		lazyEnergyHandler.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("Inventory", itemHandler.serializeNBT());
		tag.putInt("Energy", energyHandler.getEnergyStored());
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		itemHandler.deserializeNBT(tag.getCompound("Inventory"));
		energyHandler.setEnergy(tag.getInt("Energy"));
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	public IEnergyStorage getEnergyStorage() {
		return energyHandler;
	}

	public void setEnergyLevel(int energy) {
		this.energyHandler.setEnergy(energy);
	}

	public boolean hasEnoughEnergy(MachineBlockEntity blockEntity) {
		return blockEntity.energyHandler.getEnergyStored() >= energyRequired;
	}

	public void extractEnergy(MachineBlockEntity blockEntity) {
		blockEntity.energyHandler.extractEnergy(energyRequired, false);
	}

	protected abstract ItemStackHandler createInventoryItemHandler();

	protected abstract ModEnergyStorage createEnergyHandler();

	public boolean shouldTick() {
		if (tickCounter < tickDelay) {
			tickCounter++;
			return false;
		} else {
			tickCounter = 0;
		}
		return true;
	}

	public AABB getWorkArea() {
		return getWorkArea(Direction.NORTH);
	}

	public AABB getWorkArea(Direction direction) {
		return RadiusUtils.cubefromCenter(this.worldPosition, 0);
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) { }

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) { }
}
