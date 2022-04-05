package andronomos.androtech.block.entity;

import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class FarmlandHydratorBE extends BaseContainerBlockEntity {
	public FarmlandHydratorBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.FARMLAND_HYDRATOR.get(), pos, state);
	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, FarmlandHydratorBE farmlandHydrator) {

	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return null;
	}
}
