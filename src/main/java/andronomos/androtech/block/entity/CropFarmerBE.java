package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.base.AbstractTickingMachineEntity;
import andronomos.androtech.block.harvester.*;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CropFarmerBE extends AbstractTickingMachineEntity {

	private final List<IHarvester> harvesters = new ArrayList<>();

	public CropFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CROP_FARMER.get(), pos, state);

		harvesters.add(new NetherWartHarvester());
		harvesters.add(new CropHarvester());
		harvesters.add(new StemGrownBlockHarvester());
		harvesters.add(new SweetBerryHarvester());
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return new AABB(x - 4, y + 1, z - 4, x + 4, y + 1, z + 4);
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

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(!shouldTick()) return;

		AABB workArea = getWorkArea(pos);

		List<BlockPos> nearbyCrops = BlockUtil.getNearbyCrops(workArea, level);

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
}
