package andronomos.andromobtools.block.pad.emiiter.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PadEffectAcceleration implements PadEffect {
    private final double velocity;
    private final boolean moveUp;

    public PadEffectAcceleration(double velocity, boolean moveUp) {
        this.velocity = velocity;
        this.moveUp = moveUp;
    }

    @Override
    public void apply(BlockState state, Level level, BlockPos pos, Entity entity) {
        final Direction direction;

        if(moveUp) {
            direction = Direction.UP;
            entity.setDeltaMovement(0, this.velocity, 0);
        }
        else {
            direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            entity.setDeltaMovement(entity.getDeltaMovement().add(this.velocity * (direction.getStepX()), 0, this.velocity * (direction.getStepZ())));
        }
    }
}
