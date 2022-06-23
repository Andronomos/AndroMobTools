package andronomos.androtech.block.blockminer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.block.TickingMachineBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.InventoryUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockMinerBE extends TickingMachineBlockEntity {
	public ItemStackHandler pickaxeSlot;

	private final LazyOptional<IItemHandler> pickaxeSlotHandler = LazyOptional.of(() -> pickaxeSlot);
	public final LazyOptional<IItemHandler> everything = LazyOptional.of(() -> new CombinedInvWrapper(pickaxeSlot, inventoryItems));

	public BlockMinerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BLOCK_MINER.get(), pos, state);
		pickaxeSlot = new ItemStackHandler() {
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return stack.getItem() instanceof PickaxeItem;
			}
		};
		tickDelay = Const.TicksInSeconds.TWO;
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_MACHINE_MEDIUM_SIZE) {
			@Override
			public int getSlotLimit(int slotId) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slotId, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (!state.getValue(BlockMiner.POWERED)) return;
		if (!shouldTick()) return;

		FakePlayer fakePlayer = FakePlayerFactory.get(level, AndroTech.PROFILE);
		BlockPos targetPos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
		BlockState targetState = level.getBlockState(targetPos);

		AndroTech.LOGGER.info("BlockMinerBE#serverTick | getDestroySpeed: {}", targetState.getDestroySpeed(level, targetPos));

		if(targetState.isAir() || targetState.getDestroySpeed(level, targetPos) < 0) return;

		ItemStack pickaxe = pickaxeSlot.getStackInSlot(0);

		if (pickaxe.isEmpty()) return;

		fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);

		boolean canHarvest = targetState.canHarvestBlock(level, targetPos, fakePlayer);

		if (!canHarvest) return;

		LootContext.Builder builder = new LootContext.Builder(level)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(targetPos))
				.withParameter(LootContextParams.TOOL, pickaxe);

		List<ItemStack> drops = targetState.getDrops(builder);

		level.destroyBlock(targetPos, false);

		drops.forEach(stack -> {
			ItemStack returnStack = InventoryUtil.insertIntoInventory(stack, inventoryItems);

			if(!returnStack.isEmpty()) {
				ItemStackUtil.drop(level, targetPos, returnStack);
			}
		});

		ItemStackUtil.applyDamage(fakePlayer, pickaxe, 1);
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			this.setChanged();

			//If block is broken
			if(level != null && level.getBlockState(getBlockPos()).getBlock() != getBlockState().getBlock()) {
				return everything.cast();
			}

			if(side == null) {
				return inventoryHandler.cast();
			}

			if(side == Direction.DOWN) {
				return inventoryHandler.cast();
			} else {
				return pickaxeSlotHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Pickaxe", pickaxeSlot.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Pickaxe")) {
			pickaxeSlot.deserializeNBT(tag.getCompound("Pickaxe"));
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		pickaxeSlotHandler.invalidate();
	}
}
