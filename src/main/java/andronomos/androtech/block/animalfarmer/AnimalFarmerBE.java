package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.Const;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class AnimalFarmerBE  extends TickingBE {
	public AnimalFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ANIMAL_FARMER.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(Const.CONTAINER_GENERIC_SIZE) {
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
}
