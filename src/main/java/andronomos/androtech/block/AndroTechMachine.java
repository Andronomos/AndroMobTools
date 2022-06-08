package andronomos.androtech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class AndroTechMachine extends Block {
	public boolean useDefaultTopTexture;
	public boolean useDefaultBottomTexture;
	public boolean hasmultipleStates;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public AndroTechMachine(Properties properties) {
		this(properties, true, true, false);
	}

	public AndroTechMachine(Properties properties, boolean useDefaultTopTexture, boolean useDefaultBottomTexture, boolean hasmultipleStates) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
		this.useDefaultTopTexture = useDefaultTopTexture;
		this.useDefaultBottomTexture = useDefaultBottomTexture;
		this.hasmultipleStates = hasmultipleStates;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			level.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
				for(int i = 0; i < itemHandler.getSlots(); i++) {
					popResource(level, pos, itemHandler.getStackInSlot(i));
				}
			});
			level.updateNeighbourForOutputSignal(pos, this);
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
