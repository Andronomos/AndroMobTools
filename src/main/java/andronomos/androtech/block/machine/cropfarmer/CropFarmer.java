package andronomos.androtech.block.machine.cropfarmer;

import andronomos.androtech.block.machine.GuiMachine;
import andronomos.androtech.block.IPoweredBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CropFarmer extends GuiMachine implements LiquidBlockContainer, IPoweredBlock, EntityBlock {
	public static final String DISPLAY_NAME = "screen.androtech.crop_farmer";
	public static final String TOOLTIP = "block.androtech.crop_farmer.tooltip";

	public CropFarmer(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
		setTexture("top", "crop_farmer_top");
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CropFarmerBlockEntity(pos, state);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			level.getBlockEntity(pos).getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
				for(int i = 0; i < itemHandler.getSlots(); i++) {
					popResource(level, pos, itemHandler.getStackInSlot(i));
				}
			});
			level.updateNeighbourForOutputSignal(pos, this);
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(blockEntity instanceof CropFarmerBlockEntity cropFarmerBlockEntity) {
				if(level.isClientSide()) {
					cropFarmerBlockEntity.clientTick(level2, pos, state2, cropFarmerBlockEntity);
				} else {
					cropFarmerBlockEntity.serverTick((ServerLevel) level2, pos, state2, cropFarmerBlockEntity);
				}
			}
		};
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof CropFarmerBlockEntity cropFarmerBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, cropFarmerBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Override
	public FluidState getFluidState(BlockState p_60577_) {
		return Fluids.WATER.getFlowing(1, false);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor accessor, BlockPos pos, BlockState blockState, FluidState fluidState) {
		return false;
	}
}
