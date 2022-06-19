package andronomos.androtech.block.itemmender;

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

public class ItemMender extends AndroTechTickingMachine {
	public static final String SCREEN_ITEM_MENDER = "screen.androtech.item_mender";

	public ItemMender(BlockBehaviour.Properties properties) {
		super(properties, true, true, false, false);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ItemMenderBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof ItemMenderBE mendingStation) mendingStation.serverTick((ServerLevel) level2, pos, state2, mendingStation);
			}
		};
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof ItemMenderBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public TextComponent getDisplayName() {
					return new TextComponent(SCREEN_ITEM_MENDER);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
					return new ItemMenderContainer(windowId, pos, inventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
		}
	}
}
