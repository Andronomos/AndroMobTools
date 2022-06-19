package andronomos.androtech.block.amethystharvester;

import andronomos.androtech.block.AndroTechTickingMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AmethystHarvester extends AndroTechTickingMachine {

	public AmethystHarvester(Properties properties) {
		super(properties);
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {

	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AmethystHarvesterBE(pos, state);
	}
}
