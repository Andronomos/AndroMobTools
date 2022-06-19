package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.block.AndroTechTickingMachine;
import andronomos.androtech.block.cropfarmer.CropFarmerBE;
import andronomos.androtech.block.cropfarmer.CropFarmerContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
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

public class AnimalFarmer  extends AndroTechTickingMachine {
	public static final String SCREEN_ANIMAL_FARMER = "screen.androtech.animal_farmer";

	public AnimalFarmer(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AnimalFarmerBE(pos, state);
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {
		BlockEntity entity = level.getBlockEntity(pos);

		if(entity instanceof AnimalFarmerBE) {
			MenuProvider containerProvider = new MenuProvider() {
				@Override
				public TextComponent getDisplayName() {
					return new TextComponent(SCREEN_ANIMAL_FARMER);
				}

				@Override
				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
					return new AnimalFarmerContainer(windowId, pos, inventory);
				}
			};
			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
		}
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return (level2, pos, state2, blockEntity) -> {
			if(blockEntity instanceof AnimalFarmerBE animalFarmerBE) {
				if(level.isClientSide()) {
					animalFarmerBE.clientTick(level2, pos, state2, animalFarmerBE);
				} else {
					animalFarmerBE.serverTick((ServerLevel) level2, pos, state2, animalFarmerBE);
				}
			}
		};
	}
}
