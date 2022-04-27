package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.block.harvester.*;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CropHarvesterBE extends BaseContainerBlockEntity implements TickingBlockEntity {
	private int tickDelay = Const.TicksInSeconds.FIVEMINUTES;
	private int tickCounter = 0;
	private final List<IHarvester> harvesters = new ArrayList<>();

	public CropHarvesterBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CROP_HARVESTER.get(), pos, state);

		harvesters.add(new NetherWartHarvester());
		harvesters.add(new CropHarvester());
		harvesters.add(new StemGrownBlockHarvester());
		harvesters.add(new SweetBerryHarvester());
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
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {

	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(!shouldTick()) {
			return;
		}

		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		AABB area = new AABB(x - 4, y + 1, z - 4, x + 4, y + 1, z + 4);

		List<BlockPos> nearbyCrops = BlockUtil.getNearbyCrops(area, level);

		for (BlockPos nearbyCropPos : nearbyCrops) {
			BlockState cropState = level.getBlockState(nearbyCropPos);
			Block block = cropState.getBlock();

			for (IHarvester harvester : harvesters) {
				boolean harvestSuccessful = harvester.tryHarvest(block, cropState, level, nearbyCropPos, itemHandler);

				if(harvestSuccessful) {
					break;
				}
			}
		}
	}

	@Override
	public boolean shouldTick() {
		if (tickCounter < tickDelay) {
			tickCounter++;
			return false;
		} else {
			tickCounter = 0;
		}
		return true;
	}
}
