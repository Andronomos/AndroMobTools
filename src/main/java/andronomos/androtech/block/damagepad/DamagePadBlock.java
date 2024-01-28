package andronomos.androtech.block.damagepad;

import andronomos.androtech.block.FlatMachineBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamagePadBlock extends FlatMachineBlock {
	public static final int SLOTS = 4;
	public static final int AUGMENT_STACK_LIMIT = 10;
	public static final String DISPLAY_NAME = "screen.androtech.damage_pad";
	public static final String TOOLTIP = "block.androtech.damage_pad.tooltip";

	public DamagePadBlock(Properties properties) {
		super(properties, true, false, "damage_pad_top", null);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return BlockEntityRegistry.DAMAGE_PAD_BE.get().create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
			if(blockEntity instanceof DamagePadBlockEntity damagePadBlockEntity) {
				damagePadBlockEntity.serverTick((ServerLevel)level1, pos, state1, damagePadBlockEntity);
			}
		};
	}

	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof DamagePadBlockEntity damagePadBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, damagePadBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}
}
