package andronomos.androtech.block.base;

import andronomos.androtech.block.base.ATBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

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
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide) {
			OpenScreen(level, pos, player);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
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
