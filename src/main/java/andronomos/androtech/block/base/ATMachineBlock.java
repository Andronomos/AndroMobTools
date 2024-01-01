package andronomos.androtech.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public abstract class ATMachineBlock extends ATBlock {
	public boolean hasMultipleStates;

	public ATMachineBlock(Properties properties, boolean hasMultipleStates) {
		super(properties);
		this.hasMultipleStates = hasMultipleStates;
		setTexture("top", "machine_top");
		setTexture("bottom", "machine_bottom");
		setTexture("side", "machine_side");
		setTexture("front", "machine_side");
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
		if(!level.isClientSide) {
			OpenScreen(level, pos, player);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			final BlockEntity entity = level.getBlockEntity(pos);
			if(entity != null) {
				entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
					for(int i = 0; i < itemHandler.getSlots(); i++) {
						popResource(level, pos, itemHandler.getStackInSlot(i));
					}
					level.updateNeighbourForOutputSignal(pos, this);
				});
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	public abstract void OpenScreen(Level level, BlockPos pos, Player player);
}
