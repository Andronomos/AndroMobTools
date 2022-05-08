package andronomos.androtech.block.machine;

import andronomos.androtech.block.entity.MendingStationBE;
import andronomos.androtech.block.machine.base.AbstractGuiMachine;
import andronomos.androtech.inventory.MendingStationContainer;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class MendingStation extends AbstractGuiMachine {
	public static final String SCREEN_MENDING_STATION = "screen.androtech.mending_station";

	public MendingStation(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MendingStationBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof MendingStationBE mendingStation) mendingStation.serverTick((ServerLevel) level2, pos, state2, mendingStation);
			}
		};
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof MendingStationBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public TextComponent getDisplayName() {
					return new TextComponent(SCREEN_MENDING_STATION);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
					return new MendingStationContainer(windowId, pos, inventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
		}
	}
}
