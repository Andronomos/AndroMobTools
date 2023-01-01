package andronomos.androtech.block.machine;

import andronomos.androtech.Const;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class TickingMachineBlockEntity extends MachineBlockEntity {
	public int tickDelay = Const.TicksInSeconds.THREE;
	public int tickCounter = 0;

	public TickingMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) { }

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) { }

	@Override
	public boolean isRemoved() {
		return false;
	}

	public boolean shouldTick() {
		if (tickCounter < tickDelay) {
			tickCounter++;
			return false;
		} else {
			tickCounter = 0;
		}
		return true;
	}

	public AABB getWorkArea() {
		return getWorkArea(Direction.NORTH);
	}

	public AABB getWorkArea(Direction direction) {
		return RadiusUtils.cubefromCenter(this.worldPosition, 0);
	}
}
