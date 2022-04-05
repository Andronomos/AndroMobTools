package andronomos.androtech.block;

import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.inventory.MobClonerContainer;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class MobClonerBlock extends Block implements EntityBlock {
	public static final String SCREEN_MOB_CLONER = "screen.mobtools.mob_cloner";

	public MobClonerBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MobClonerBE(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(level.isClientSide()) {
				if(blockEntity instanceof MobClonerBE mobCloner) mobCloner.clientTick(level2, pos, state2, mobCloner);
			} else {
				if(blockEntity instanceof MobClonerBE mobCloner) mobCloner.serverTick((ServerLevel) level2, pos, state2, mobCloner);
			}
		};
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide()) {
			BlockEntity be = level.getBlockEntity(pos);

			if (be instanceof MobClonerBE generator) {
				MenuProvider containerProvider = new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return new TranslatableComponent(SCREEN_MOB_CLONER);
					}

					@Override
					public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
						return new MobClonerContainer(windowId, pos, playerInventory, playerEntity);
					}
				};
				NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMOving) {
		if(state.getBlock() != newState.getBlock()) {
			final MobClonerBE tileEntity = (MobClonerBE)level.getBlockEntity(pos);

			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
				ItemStack magicLead = itemHandler.getStackInSlot(0);
				ItemStackUtil.drop(level, pos, magicLead);
			});
		}

		super.onRemove(state, level, pos, newState, isMOving);
	}
}
