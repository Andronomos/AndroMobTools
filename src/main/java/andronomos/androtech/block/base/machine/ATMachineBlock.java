package andronomos.androtech.block.base.machine;

import andronomos.androtech.block.base.ATBlock;

public class ATMachineBlock extends ATBlock {
	public boolean hasMultipleStates;

	public ATMachineBlock(Properties properties, boolean hasMultipleStates) {
		super(properties);
		this.hasMultipleStates = hasMultipleStates;
		setTexture("top", "machine_top");
		setTexture("bottom", "machine_bottom");
		setTexture("side", "machine_side");
		setTexture("front", "machine_side");
	}
}
