package andronomos.androtech.block.pad.damagepad;

import andronomos.androtech.block.pad.PadBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
		return BlockEntityRegistry.DAMAGE_PAD.get().create(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
			BlockEntity entity = level.getBlockEntity(pos);

			if(entity instanceof DamagePadBlockEntity) {
				//todo: open screen
			} else {

			}

			//debug
			player.sendSystemMessage(Component.literal("DamagePadBlock#use"));
			return InteractionResult.sidedSuccess(false);
		}

		return super.use(state, level, pos, player, hand, hitResult);
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

			//todo: remove inventory items

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
