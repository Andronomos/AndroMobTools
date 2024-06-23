package andronomos.androtech.block.ExperienceAttractor;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.network.AndroTechPacketHandler;
import andronomos.androtech.network.packet.FluidSyncPacket;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.FluidRegistry;
import andronomos.androtech.util.BoundingBoxHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ExperienceAttractorBlockEntity extends BaseBlockEntity implements MenuProvider {
	public int prevTankAmount;
	private final FluidTank FLUID_TANK = new FluidTank(1000 * 16) {
		@Override
		protected void onContentsChanged() {
			setChanged();
			//if(!level.isClientSide()) {
			//	AndroTechPacketHandler.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
			//}
		}

		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid() == FluidRegistry.LIQUID_XP.get();
		}
	};
	protected LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

	public ExperienceAttractorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.EXPERIENCE_ATTRACTOR_BE.get(), pos, state, new SimpleContainerData(0));
	}

	public void setFluid(FluidStack stack) {
		this.FLUID_TANK.setFluid(stack);
	}

	public FluidTank getFluidTank() {
		return this.FLUID_TANK;
	}

	public FluidStack getFluidStack() {
		return this.FLUID_TANK.getFluid();
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return null;
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(ExperienceAttractorBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
		//AndroTechPacketHandler.sendToClients(new FluidSyncPacket(this.getFluidStack(), worldPosition));
		return new ExperienceAttractorMenu(containerId, playerInventory, this, this.data);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (lazyFluidHandler != null) {
			lazyFluidHandler.invalidate();
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		if (lazyFluidHandler != null) {
			FLUID_TANK.writeToNBT(tag);
		}
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		if (lazyFluidHandler != null) {
			FLUID_TANK.readFromNBT(tag);
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

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == ForgeCapabilities.FLUID_HANDLER) {
			return lazyFluidHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public AABB getWorkArea() {
		return BoundingBoxHelper.nineByNineByNineBoxFromCenter(getBlockPos());
	}

	@Override
	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) {
		if(!state.getValue(POWERED)) {
			return;
		}

		if(blockEntity instanceof ExperienceAttractorBlockEntity entity) {
			entity.prevTankAmount = entity.FLUID_TANK.getFluidAmount();

			if(level.getGameTime() % 10 == 0) {
				if(entity.FLUID_TANK.isEmpty() || entity.FLUID_TANK.getFluid().containsFluid(new FluidStack(FluidRegistry.LIQUID_XP.get(), 1))) {
					CaptureExperience();
				}
			}

			if(entity.prevTankAmount != entity.FLUID_TANK.getFluidAmount()) {
				entity.sendUpdate();
			}
		}
	}

	private boolean CaptureExperience() {
		for(ExperienceOrb orb : getNearbyExperience()) {
			int xpAmount = orb.getValue();

			if(FLUID_TANK.getFluidAmount() < FLUID_TANK.getCapacity() - xpAmount * 20) {
				FLUID_TANK.fill(new FluidStack(FluidRegistry.LIQUID_XP.get(), xpAmount * 20), FluidAction.EXECUTE);
				orb.value = 0;
				orb.remove(RemovalReason.DISCARDED);
			}

			return true;
		}

		return false;
	}

	private List<ExperienceOrb> getNearbyExperience() {
		return Objects.requireNonNull(getLevel()).getEntitiesOfClass(ExperienceOrb.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private void sendUpdate() {
		getLevel().sendBlockUpdated(worldPosition, getLevel().getBlockState(worldPosition), getLevel().getBlockState(worldPosition), Block.UPDATE_ALL);
	}
}
