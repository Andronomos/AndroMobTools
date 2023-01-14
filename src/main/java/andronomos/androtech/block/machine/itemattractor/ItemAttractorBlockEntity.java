package andronomos.androtech.block.machine.itemattractor;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineTickingBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.InventoryUtils;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemAttractorBlockEntity extends MachineTickingBlockEntity implements MenuProvider {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ItemAttractorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ITEM_ATTRACTOR.get(), pos, state);
	}

	@Nonnull
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.INVENTORY_VANILLA_LARGE_SIZE) {
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

	@Override
	public AABB getWorkArea() {
		return RadiusUtils.nineByNineByNineCubeFromCenter(getBlockPos());
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(ItemAttractor.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new ItemAttractorMenu(pContainerId, pPlayerInventory, this);
	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, ItemAttractorBlockEntity itemAttractor) {
		if(!state.getValue(POWERED)) return;
		if(!shouldTick()) return;

		if(!InventoryUtils.inventoryIsFull(itemHandler)) {
			captureDroppedItems();
		}

		deleteCapturedXp();
	}

	private void captureDroppedItems() {
		for(ItemEntity item : getCapturedItems()) {
			if(item == null)
				return;

			ItemStack stack = InventoryUtils.insertIntoInventory(item.getItem().copy(), itemHandler, false);

			if (!stack.isEmpty()) {
				item.setItem(stack);
			} else {
				item.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	private List<ItemEntity> getCapturedItems() {
		return getLevel().getEntitiesOfClass(ItemEntity.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private List<ExperienceOrb> getCapturedXP() {
		return getLevel().getEntitiesOfClass(ExperienceOrb.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private void deleteCapturedXp() {
		for(ExperienceOrb orb : getCapturedXP()) {
			orb.remove(Entity.RemovalReason.KILLED);
		}
	}
}
