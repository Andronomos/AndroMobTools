package andronomos.androtech.block.itemattractor;

import andronomos.androtech.Const;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemAttractorBE extends TickingBE {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ItemAttractorBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ITEM_ATTRACTOR.get(), pos, state);
	}

	@Nonnull
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_GENERIC_LARGE_SIZE) {
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

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, ItemAttractorBE itemAttractor) {
		if(state.getValue(POWERED)) {
			if(!shouldTick()) return;

			if(!InventoryUtil.inventoryIsFull(this.inventoryItems)) {
				captureDroppedItems();
			}
			deleteCapturedXp();
		}
	}

	private void captureDroppedItems() {
		for(ItemEntity item : getCapturedItems()) {
			if(item == null)
				return;

			ItemStack stack = InventoryUtil.insertIntoInventory(item.getItem().copy(), inventoryItems);

			if (!stack.isEmpty()) {
				item.setItem(stack);
			} else {
				item.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	public List<ItemEntity> getCapturedItems() {
		return getLevel().getEntitiesOfClass(ItemEntity.class, getAABBWithModifiers(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	public List<ExperienceOrb> getCapturedXP() {
		return getLevel().getEntitiesOfClass(ExperienceOrb.class, getAABBWithModifiers(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private void deleteCapturedXp() {
		for(ExperienceOrb orb : getCapturedXP()) {
			orb.remove(Entity.RemovalReason.KILLED);
		}
	}

	private AABB getAABBWithModifiers() {
		double x = getBlockPos().getX();
		double y = getBlockPos().getY();
		double z = getBlockPos().getZ();
		return new AABB(x - 9D, y - 9D, z - 9D, x + 9D, y + 9D, z + 9D);
	}
}
