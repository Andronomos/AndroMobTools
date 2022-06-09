package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.block.AndroTechTickingMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AnimalFarmer  extends AndroTechTickingMachine {
	public static final String SCREEN_ANIMAL_FARMER = "screen.androtech.animal_farmer";

	public AnimalFarmer(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AnimalFarmerBE(pos, state);
	}

	@Override
	public void OpenGui(Level level, BlockPos pos, Player player) {

	}
}
