package andronomos.androtech.block.amethystharvester;

import andronomos.androtech.Const;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.inventory.MachineSlotItemStackHandler;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AmethystHarvesterBE extends TickingBE {
	public ItemStackHandler pickaxeSlot;

	private final LazyOptional<IItemHandler> pickaxeSlotHandler = LazyOptional.of(() -> pickaxeSlot);
	public final LazyOptional<IItemHandler> everything = LazyOptional.of(() -> new CombinedInvWrapper(pickaxeSlot, inventoryItems));

	public AmethystHarvesterBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AMETHYST_HARVESTER.get(), pos, state);
		pickaxeSlot = new MachineSlotItemStackHandler(Items.DIAMOND_PICKAXE);
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
		if(!shouldTick()) return;

		//todo mine amethyst buds in a 9x9 area
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
