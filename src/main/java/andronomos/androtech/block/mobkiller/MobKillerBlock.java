package andronomos.androtech.block.mobkiller;

import andronomos.androtech.block.DirectionalMachineBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobKillerBlock extends DirectionalMachineBlock {
	public static final int SLOTS = 3;
	public static final int AUGMENT_STACK_LIMIT = 20;
	public static final String DISPLAY_NAME = "screen.androtech.mob_killer";
	public static final String TOOLTIP = "block.androtech.mob_killer.tooltip";

	public MobKillerBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return BlockEntityRegistry.MOB_KILLER_BE.get().create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
			if(blockEntity instanceof MobKillerBlockEntity mobKillerBlockEntity) {
				mobKillerBlockEntity.serverTick((ServerLevel)level1, pos, state1, mobKillerBlockEntity);
			}
		};
	}

	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof MobKillerBlockEntity mobKillerBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, mobKillerBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Override
	public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
		return 2400.0F;
	}
}
