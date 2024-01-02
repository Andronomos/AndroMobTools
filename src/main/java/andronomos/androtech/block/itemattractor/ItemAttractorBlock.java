package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.MachineBlock;
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
import org.jetbrains.annotations.Nullable;

public class ItemAttractorBlock extends MachineBlock {
	public static final String DISPLAY_NAME = "screen.androtech.item_attractor";
	public static final String TOOLTIP = "block.androtech.item_attractor.tooltip";

	public ItemAttractorBlock(Properties properties) {
		super(properties, true, false, false);
		addTexture("top_off", "item_attractor_off_top");
		addTexture("side_off", "item_attractor_off_side");
		addTexture("bottom_off", "item_attractor_off_bottom");
		addTexture("top_on", "item_attractor_on_top");
		addTexture("side_on", "item_attractor_on_side");
		addTexture("bottom_on", "item_attractor_on_bottom");
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ItemAttractorBlockEntity(pos, state);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity instanceof ItemAttractorBlockEntity itemAttractorBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, itemAttractorBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(!level.isClientSide()) {
				if(blockEntity instanceof ItemAttractorBlockEntity itemAttractor) itemAttractor.serverTick((ServerLevel) level2, pos, state2, itemAttractor);
			}
		};
	}
}
