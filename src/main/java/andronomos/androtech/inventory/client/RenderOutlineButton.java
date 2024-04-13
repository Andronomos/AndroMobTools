package andronomos.androtech.inventory.client;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class RenderOutlineButton extends SideButton {
	public static final String TOOLTIP_ON = "gui.androtech.overlay_on";
	public static final String TOOLTIP_OFF = "gui.androtech.overlay_off";
	private static final TextureEnum TEXTURE_OFF = TextureEnum.OVERLAY_OFF;
	private static final TextureEnum TEXTURE_ON = TextureEnum.OVERLAY_ON;

	public RenderOutlineButton(OnPress onPress) {
		super(TEXTURE_OFF, onPress);
	}

	public void update(boolean showRenderBox) {
		boolean isOn = showRenderBox;
		setCurrentTexture(isOn ? TEXTURE_ON : TEXTURE_OFF);
		Tooltip t = Tooltip.create(Component.translatable(isOn ? TOOLTIP_ON : TOOLTIP_OFF));
		setTooltip(t);
	}
}
