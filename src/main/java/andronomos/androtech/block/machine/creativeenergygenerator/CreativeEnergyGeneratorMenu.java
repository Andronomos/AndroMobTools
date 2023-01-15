package andronomos.androtech.block.machine.creativeenergygenerator;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CreativeEnergyGeneratorMenu extends MachineMenu {
	public CreativeEnergyGeneratorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
	}

	public CreativeEnergyGeneratorMenu(int id, Inventory inv, BlockEntity entity) {
		super(ModMenuTypes.CREATIVE_ENERGY_GENERATOR.get(), id, inv);
		this.player = inv.player;

		if(entity != null && entity instanceof CreativeEnergyGeneratorBlockEntity creativeEnergyGeneratorBlockEntity) {
			blockEntity = creativeEnergyGeneratorBlockEntity;
		}

		setupSlotIndexs(0);
		layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.CREATIVE_ENERGY_GENERATOR.get());
	}
}
