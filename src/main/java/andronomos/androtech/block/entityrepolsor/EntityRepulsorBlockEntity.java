package andronomos.androtech.block.entityrepolsor;

import andronomos.androtech.block.BaseBlockEntity;
import andronomos.androtech.block.damagepad.DamagePadBlock;
import andronomos.androtech.block.damagepad.DamagePadMenu;
import andronomos.androtech.block.itemattractor.ItemAttractorBlock;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlock;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityRepulsorBlockEntity extends BaseBlockEntity implements MenuProvider {
	public EntityRepulsorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.ENTITY_REPULSOR_BE.get(), pos, state, new SimpleContainerData(EntityRepulsorBlock.SLOTS));
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(EntityRepulsorBlock.SLOTS) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable(EntityRepulsorBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new EntityRepulsorMenu(containerId, inventory, this, this.data);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		if(!state.getValue(POWERED)) return;


	}
}
