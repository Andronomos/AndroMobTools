package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.base.AbstractTickingMachineBE;
import andronomos.androtech.item.module.ItemRepairModule;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MendingStationBE extends AbstractTickingMachineBE {
	public MendingStationBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.MENDING_STATION.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_GENERIC_SIZE) {
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
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(shouldTick()) {
			getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
				for (int i = 0; i < itemHandler.getSlots(); i++) {
					ItemStack itemstack = itemHandler.getStackInSlot(i);
					if (!ItemStackUtil.isRepairable(itemstack)) continue;
					itemstack.setDamageValue(itemstack.getDamageValue() - ItemRepairModule.REPAIR_MODULE_RATE);
				}
			});
		}
	}
}
