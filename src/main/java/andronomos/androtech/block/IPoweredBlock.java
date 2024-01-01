package andronomos.androtech.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface IPoweredBlock {
	String GUI_ON = "gui.androtech.powered_on";
	String GUI_OFF = "gui.androtech.powered_off";
	BooleanProperty POWERED = BlockStateProperties.POWERED;
}
