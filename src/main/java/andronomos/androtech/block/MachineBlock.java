package andronomos.androtech.block;

import andronomos.androtech.base.BaseMachineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class MachineBlock extends BaseMachineBlock implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public MachineBlock(Properties properties)
	{
		this(properties, false);
	}

	public MachineBlock(Properties properties, boolean hasTooltip) {
		super(properties, hasTooltip);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}
}
