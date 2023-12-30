package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.block.pad.PadBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class DamagePadBlock extends PadBlock implements EntityBlock {
	public static final int PAD_SLOTS = 5;
	public static final int UPGRADE_STACK_LIMIT = 10;
	public static final String DISPLAY_NAME = "screen.androtech.damage_pad";
	public static final String TOOLTIP = "block.androtech.damage_pad.tooltip";

	public DamagePadBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityRegistry.DAMAGE_PAD_BE.get().create(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!level.isClientSide) {
			BlockEntity entity = level.getBlockEntity(pos);
			if(entity instanceof DamagePadBlockEntity damagePadBlockEntity) {
				NetworkHooks.openScreen((ServerPlayer)player, damagePadBlockEntity, pos); //this will need to be replaced in 1.20.2
			} else {
				throw new IllegalStateException("Missing container provider");
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
			if(blockEntity instanceof DamagePadBlockEntity damagePadBlockEntity) {
				damagePadBlockEntity.serverTick();
			}
		};
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			final DamagePadBlockEntity entity = (DamagePadBlockEntity)level.getBlockEntity(pos);
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
				for(int i = 0; i < itemHandler.getSlots(); i++) {
					popResource(level, pos, itemHandler.getStackInSlot(i));
				}
				level.updateNeighbourForOutputSignal(pos, this);
			});
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
