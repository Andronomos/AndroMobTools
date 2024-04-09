package andronomos.androtech.block.entityrepolsor;

import andronomos.androtech.block.MachineBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class EntityRepulsorBlock extends MachineBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public EntityRepulsorBlock(Properties properties) {
		super(properties, true, false, true);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
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
