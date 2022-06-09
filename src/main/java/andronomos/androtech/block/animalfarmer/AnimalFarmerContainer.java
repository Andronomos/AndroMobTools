package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.block.BaseContainerMenu;
import andronomos.androtech.block.cropfarmer.CropFarmerBE;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class AnimalFarmerContainer extends BaseContainerMenu {
	public final CropFarmerBE blockEntity;

	public AnimalFarmerContainer(int windowId, BlockPos pos, Inventory inventory) {
		super(ModContainers.ANIMAL_FARMER.get(), windowId, inventory);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), this.player, ModBlocks.ANIMAL_FARMER.get());
	}
}
