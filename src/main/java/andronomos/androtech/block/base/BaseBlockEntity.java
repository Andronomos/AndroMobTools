package andronomos.androtech.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public abstract class BaseBlockEntity extends BlockEntity {
	public final ItemStackHandler itemHandler = createInventoryItemHandler();

	public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	protected abstract ItemStackHandler createInventoryItemHandler();
}
