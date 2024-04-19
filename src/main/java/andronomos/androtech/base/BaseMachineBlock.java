package andronomos.androtech.base;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BaseMachineBlock extends Block implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public BaseMachineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return null;
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			final BlockEntity entity = level.getBlockEntity(pos);
			if(entity != null) {
				entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
					for(int i = 0; i < itemHandler.getSlots(); i++) {
						popResource(level, pos, itemHandler.getStackInSlot(i));
					}
					level.updateNeighbourForOutputSignal(pos, this);
				});
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
		if(!level.isClientSide) {
			OpenScreen(level, pos, player);
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	public void OpenScreen(Level level, BlockPos pos, Player player) {
		//do nothing if the block doesn't have a screen
	}
}
