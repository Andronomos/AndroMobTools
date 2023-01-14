package andronomos.androtech.gui.widget.button.sidebutton;

import andronomos.androtech.gui.TextureEnum;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public class PowerButton extends SideButton {
	private final BlockEntity blockEntity;
	private String tooltipPrefix;
	public static final TextureEnum textureOff = TextureEnum.POWERED_OFF;
	public static final TextureEnum textureOn = TextureEnum.POWERED_ON;

	public PowerButton(OnPress onPress, BlockEntity blockEntity) {
		super(textureOff, onPress);
		this.blockEntity = blockEntity;
		this.tooltipPrefix = "gui.androtech.powered";
	}

	public void update() {
		boolean isOn = blockEntity.getBlockState().getValue(BlockStateProperties.POWERED);
		setCurrentTexture(isOn ? textureOn : textureOff);
		setTooltip(this.tooltipPrefix + (isOn ? "_on" : "_off"));
	}
}
