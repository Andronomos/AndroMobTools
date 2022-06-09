package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.block.MachineScreen;
import andronomos.androtech.gui.widget.button.machinebutton.PowerButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AnimalFarmerScreen extends MachineScreen<AnimalFarmerContainer> {
	private PowerButton powerButton;
	private AnimalFarmerContainer container;

	public AnimalFarmerScreen(AnimalFarmerContainer container, Inventory inventory, Component component) {
		super(container, inventory, component);
		this.container = container;
		this.imageHeight = 222;
	}

	@Override
	protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

	}
}
