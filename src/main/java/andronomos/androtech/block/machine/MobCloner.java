package andronomos.androtech.block.machine;

import andronomos.androtech.block.entity.MobClonerBE;
import andronomos.androtech.block.machine.base.AbstractGuiMachine;
import andronomos.androtech.inventory.MobClonerContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class MobCloner extends AbstractGuiMachine {
	public static final String SCREEN_MOB_CLONER = "screen.mobtools.mob_cloner";

	public MobCloner(Properties properties) {
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
			if(blockEntity instanceof MobClonerBE mobCloner) {
				if(level.isClientSide()) {
					mobCloner.clientTick(level2, pos, state2, mobCloner);
				} else {
					mobCloner.serverTick((ServerLevel) level2, pos, state2, mobCloner);
				}
			}
		};
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity be = level.getBlockEntity(pos);

		if (be instanceof MobClonerBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return new TranslatableComponent(SCREEN_MOB_CLONER);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
					return new MobClonerContainer(windowId, pos, playerInventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
		}
	}
}
