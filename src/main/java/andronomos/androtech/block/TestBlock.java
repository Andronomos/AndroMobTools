package andronomos.androtech.block;

import andronomos.androtech.block.entity.CropHarvesterBE;
import andronomos.androtech.inventory.CropHarvesterContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class TestBlock extends Block {
	public static final String SCREEN_TEST_BLOCK = "screen.androtech.test_block";

	public TestBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			BlockEntity entity = level.getBlockEntity(pos);

			if(entity instanceof CropHarvesterBE) {
				MenuProvider containerProvider = new MenuProvider() {
					@Override
					public TextComponent getDisplayName() {
						return new TextComponent(SCREEN_TEST_BLOCK);
					}

					@Override
					public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
						return new CropHarvesterContainer(windowId, pos, inventory);
					}
				};
				NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
			}
		}

		return InteractionResult.SUCCESS;
	}
}
