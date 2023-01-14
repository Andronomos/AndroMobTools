package andronomos.androtech.gui;

public enum TextureEnum {
	POWERED_ON,
	POWERED_OFF,
	OVERLAY_ON,
	OVERLAY_OFF;

	public int getX() {
		switch(this) {
			case POWERED_OFF:
				return 0;
			case POWERED_ON:
				return 18;
			case OVERLAY_OFF:
				return 36;
			case OVERLAY_ON:
				return 54;
		}
		return 0;
	}

	public int getY() {
		switch(this) {
			case POWERED_OFF:
			case POWERED_ON:
			case OVERLAY_OFF:
			case OVERLAY_ON:
				return 0;
		}
		return 0;
	}

	public int getWidth() {
		//switch (this) {
		//	case POWERED_OFF:
		//	case POWERED_ON:
		//	case OVERLAY_OFF:
		//	case OVERLAY_ON:
		//		return 18;
		//	default:
		//		return 18;
		//}

		return 18;
	}

	public int getHeight() {
		//switch (this) {
		//	case POWERED_OFF:
		//	case POWERED_ON:
		//	case OVERLAY_OFF:
		//	case OVERLAY_ON:
		//		return 18;
		//	default:
		//		return 18;
		//}

		return 18;
	}
}
