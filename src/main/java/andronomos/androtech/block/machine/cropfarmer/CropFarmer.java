package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.block.machine.GuiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class CropFarmer extends GuiMachine implements LiquidBlockContainer {
	public CropFarmer(Properties properties) {
		super(properties);
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {

	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		//todo: return block entity
		return null;
	}

	@Override
	public FluidState getFluidState(BlockState p_60577_) {
		return Fluids.WATER.getFlowing(1, false);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter p_54766_, BlockPos p_54767_, BlockState p_54768_, Fluid p_54769_) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor p_54770_, BlockPos p_54771_, BlockState p_54772_, FluidState p_54773_) {
		return false;
	}
}
