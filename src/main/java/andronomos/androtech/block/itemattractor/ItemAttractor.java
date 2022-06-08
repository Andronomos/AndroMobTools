package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.AndroTechTickingMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ItemAttractor extends AndroTechTickingMachine {
	public static final String SCREEN_ITEM_ATTRACTOR = "screen.androtech.item_attractor";

	public ItemAttractor(BlockBehaviour.Properties properties) {
		super(properties, false, true, true);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ItemAttractorBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof ItemAttractorBE itemAttractor) itemAttractor.serverTick((ServerLevel) level2, pos, state2, itemAttractor);
			}
		};
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof ItemAttractorBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public TextComponent getDisplayName() {
					return new TextComponent(SCREEN_ITEM_ATTRACTOR);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
					return new ItemAttractorContainer(windowId, pos, inventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
		}
	}
}
