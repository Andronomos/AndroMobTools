package andronomos.androtech.gui;

public enum TextureEnum {
	POWERED_ON,
	POWERED_OFF;

	public int getX() {
		switch(this) {
			case POWERED_OFF:
				return 0;
			case POWERED_ON:
				return 18;
		}
		return 0;
	}

	public int getY() {
		switch(this) {
			case POWERED_OFF:
			case POWERED_ON:
				return 0;
		}
		return 0;
	}

	public int getWidth() {
		switch (this) {
			case POWERED_OFF:
			case POWERED_ON:
				return 18;
			default:
				return 20;
		}
	}

	public int getHeight() {
		switch (this) {
			case POWERED_OFF:
			case POWERED_ON:
				return 18;
			default:
				return 20;
		}
	}
}
