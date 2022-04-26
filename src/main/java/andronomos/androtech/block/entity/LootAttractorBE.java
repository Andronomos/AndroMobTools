package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LootAttractorBE extends BaseContainerBlockEntity {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public LootAttractorBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ITEM_ATTRACTOR.get(), pos, state);
	}

	@Nonnull
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_SIZE) {
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

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, LootAttractorBE itemAttractor) {
		if(state.getValue(POWERED)) {
			if(level.getGameTime() % 3 == 0) {
				if(!isInventoryFull()) {
					captureDroppedItems();
				}
				deleteCapturedXp();
			}
		}
	}

	private boolean isInventoryFull() {
		AtomicBoolean isFull = new AtomicBoolean(true);

		getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
			for(int i = 0; i <= itemHandler.getSlots() - 1; i++) {
				ItemStack itemstack = itemHandler.getStackInSlot(i);

				if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize())
				{
					isFull.set(false);
					break;
				}
			}
		});

		return isFull.get();
	}

	private void captureDroppedItems() {
		for(ItemEntity item : getCapturedItems()) {
			if(item == null)
				return;

			ItemStack stack = ItemStackUtil.insertIntoContainer(item.getItem().copy(), itemHandler);

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
		double x = getBlockPos().getX() + 0.5D;
		double y = getBlockPos().getY() + 0.5D;
		double z = getBlockPos().getZ() + 0.5D;
		return new AABB(x - 9.5D, y - 9.5D, z - 9.5D, x + 9.5D, y + 9.5D, z + 9.5D);
	}
}
