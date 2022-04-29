package andronomos.androtech.block;

import andronomos.androtech.block.entity.FarmerUnitBE;
import andronomos.androtech.inventory.FarmerUnitContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class FarmerUnit extends Block implements EntityBlock, LiquidBlockContainer {
	public static final String SCREEN_FARMER_UNIT = "screen.androtech.farmer_unit";

	public FarmerUnit(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FarmerUnitBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof FarmerUnitBE cropHarvester) cropHarvester.serverTick((ServerLevel) level2, pos, state2, cropHarvester);
			}
		};
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			BlockEntity entity = level.getBlockEntity(pos);

			if(entity instanceof FarmerUnitBE) {
				MenuProvider containerProvider = new MenuProvider() {
					@Override
					public TextComponent getDisplayName() {
						return new TextComponent(SCREEN_FARMER_UNIT);
					}

					@Override
					public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
						return new FarmerUnitContainer(windowId, pos, inventory);
					}
				};
				NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
			}
		}

		return InteractionResult.SUCCESS;
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

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			BlockEntity entity = level.getBlockEntity(pos);
			if(entity instanceof FarmerUnitBE) {
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
					for(int i = 0; i <= itemHandler.getSlots() - 1; i++) {
						popResource(level, pos, itemHandler.getStackInSlot(i));
					}
				});
				level.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
