//package andronomos.androtech.inventory;
//
//import andronomos.androtech.registry.ModBlocks;
//import andronomos.androtech.registry.ModContainers;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.ContainerLevelAccess;
//import net.minecraft.world.level.block.entity.BlockEntity;
//
//public class TestBlockContainer extends BaseContainerMenu {
//	public final BlockEntity blockEntity;
//
//	public TestBlockContainer(int windowId, BlockPos pos, Inventory inventory) {
//		super(ModContainers.TEST_BLOCK.get(), windowId, inventory);
//		blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);
//	}
//
//	@Override
//	public boolean stillValid(Player p_38874_) {
//		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.TEST_BLOCK.get());
//	}
//}
