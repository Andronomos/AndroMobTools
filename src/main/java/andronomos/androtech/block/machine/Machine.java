package andronomos.androtech.block.machine;

import andronomos.androtech.block.ATBlock;

public abstract class Machine extends ATBlock {
	public Machine(Properties properties) {
		super(properties);

		setTexture("top", "machine_top");
		setTexture("bottom", "machine_bottom");
		setTexture("side", "machine_side");
		setTexture("front", "machine_side");
	}
}
