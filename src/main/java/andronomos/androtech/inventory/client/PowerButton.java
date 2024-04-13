package andronomos.androtech.inventory.client;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PowerButton extends SideButton {
	private final BlockEntity blockEntity;
	public static final String TOOLTIP_ON = "gui.androtech.powered_on";
	public static final String TOOLTIP_OFF = "gui.androtech.powered_off";
	public static final TextureEnum TEXTURE_OFF = TextureEnum.POWERED_OFF;
	public static final TextureEnum TEXTURE_ON = TextureEnum.POWERED_ON;

	public PowerButton(OnPress onPress, BlockEntity blockEntity) {
		super(TEXTURE_OFF, onPress);
		this.blockEntity = blockEntity;
	}

	public void update() {
		boolean isOn = blockEntity.getBlockState().getValue(BlockStateProperties.POWERED);
		setCurrentTexture(isOn ? TEXTURE_ON : TEXTURE_OFF);
		Tooltip t = Tooltip.create(Component.translatable(isOn ? TOOLTIP_ON : TOOLTIP_OFF));
		setTooltip(t);
	}
}
