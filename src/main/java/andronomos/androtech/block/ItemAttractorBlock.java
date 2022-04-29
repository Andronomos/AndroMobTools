package andronomos.androtech.block;

import andronomos.androtech.block.entity.ItemAttractorBE;
import andronomos.androtech.inventory.LootAttractorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ItemAttractorBlock extends Block implements EntityBlock {
	public static final String SCREEN_ITEM_ATTRACTOR = "screen.androtech.item_attractor";
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ItemAttractorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
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
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			if(player.isCrouching()) {
				BlockEntity entity = level.getBlockEntity(pos);

				if(entity instanceof ItemAttractorBE) {
					MenuProvider containerProvider = new MenuProvider() {
						@Override
						public TextComponent getDisplayName() {
							return new TextComponent(SCREEN_ITEM_ATTRACTOR);
						}

						@Override
						public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
							return new LootAttractorContainer(windowId, pos, inventory);
						}
					};
					NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
				}
			} else {
				level.setBlock(pos, state.cycle(POWERED), 3);
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			BlockEntity entity = level.getBlockEntity(pos);
			if(entity instanceof ItemAttractorBE) {
				entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
					for(int i = 0; i <= itemHandler.getSlots() - 1; i++) {
						popResource(level, pos, itemHandler.getStackInSlot(i));
					}
				});
				level.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
