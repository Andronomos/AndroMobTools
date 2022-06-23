package andronomos.androtech.block.blockminer;

import andronomos.androtech.block.ATGuiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockMiner extends ATGuiMachine {
	public static final String name = "screen.androtech.block_miner";
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public BlockMiner(Properties properties) {
		super(properties, true, false, true, false, false, true);
	}

	@javax.annotation.Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);

		for(final Direction facing : context.getNearestLookingDirections()) {
			if(facing.getAxis().isHorizontal()) {
				state = state.setValue(FACING, facing.getOpposite());
				break;
			}
		}

		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof BlockMinerBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public TextComponent getDisplayName() {
					return new TextComponent(name);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
					return new BlockMinerContainer(windowId, pos, inventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlockMinerBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(blockEntity instanceof BlockMinerBE blockMinerBE) {
				if(level.isClientSide()) {
					blockMinerBE.clientTick(level2, pos, state2, blockMinerBE);
				} else {
					blockMinerBE.serverTick((ServerLevel) level2, pos, state2, blockMinerBE);
				}
			}
		};
	}
}
