package andronomos.androtech.block.machine.creativeenergygenerator;

import andronomos.androtech.block.IPoweredBlock;
import andronomos.androtech.block.machine.GuiMachine;
import andronomos.androtech.block.machine.Machine;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyGenerator extends GuiMachine implements IPoweredBlock, EntityBlock {
	public static final String DISPLAY_NAME = "screen.androtech.creative_energy_generator";
	public static final String TOOLTIP = "block.androtech.creative_energy_generator.tooltip";

	public CreativeEnergyGenerator(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
	}

	@Override
	public void OpenScreen(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof CreativeEnergyGeneratorBlockEntity creativeEnergyGeneratorBlockEntity) {
			NetworkHooks.openScreen((ServerPlayer) player, creativeEnergyGeneratorBlockEntity, entity.getBlockPos());
		} else {
			throw new IllegalStateException("Missing container provider");
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CreativeEnergyGeneratorBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(blockEntity instanceof CreativeEnergyGeneratorBlockEntity cropFarmerBlockEntity) {
				if(!level.isClientSide()) {
					cropFarmerBlockEntity.serverTick((ServerLevel) level2, pos, state2, cropFarmerBlockEntity);
				}
			}
		};
	}
}
