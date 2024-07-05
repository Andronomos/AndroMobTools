package andronomos.androtech.block.fluidtank;

import andronomos.androtech.block.base.BaseBlockEntity;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.registry.FluidRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends BaseBlockEntity implements MenuProvider {
	private static final int CAPACITY = AndroTechConfig.FLUID_TANK_CAPACITY.get();
	public int prevTankAmount;

	public FluidTankBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.FLUID_TANK_BE.get(), pos, state, new SimpleContainerData(FluidTankBlock.SLOTS));
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(FluidTankBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
		return new FluidTankMenu(containerId, playerInventory, this, this.data);
	}

	@Override
	public FluidTank createFluidHandler() {
		return new FluidTank(1000 * CAPACITY) {
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
	protected void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity blockEntity) {
		if(blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity) {
			if(fluidTankBlockEntity.prevTankAmount != fluidTankBlockEntity.getFluidStack().getAmount()) {
				level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
				fluidTankBlockEntity.setChanged();
			}
		}
	}
}
