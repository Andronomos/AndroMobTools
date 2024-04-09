package andronomos.androtech.block.entityrepolsor;

import andronomos.androtech.block.MachineBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityRepulsorBlock extends MachineBlock {
	public EntityRepulsorBlock(Properties properties) {
		super(properties, false);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		for(final Direction facing : context.getNearestLookingDirections()) {
			if(facing.getAxis().isHorizontal()) {
				state = state.setValue(FACING, facing.getOpposite());
				break;
			}
		}
		return state;
	}
}
