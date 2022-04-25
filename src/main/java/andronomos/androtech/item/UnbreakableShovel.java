package andronomos.androtech.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UnbreakableShovel extends ShovelItem {
	public UnbreakableShovel(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext p_43119_) {
		Level level = p_43119_.getLevel();
		BlockPos blockpos = p_43119_.getClickedPos();
		BlockState blockstate = level.getBlockState(blockpos);
		if (p_43119_.getClickedFace() == Direction.DOWN) {
			return InteractionResult.PASS;
		} else {
			Player player = p_43119_.getPlayer();
			BlockState blockstate1 = blockstate.getToolModifiedState(level, blockpos, player, p_43119_.getItemInHand(), net.minecraftforge.common.ToolActions.SHOVEL_FLATTEN);
			BlockState blockstate2 = null;
			if (blockstate1 != null && level.isEmptyBlock(blockpos.above())) {
				level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
				blockstate2 = blockstate1;
			} else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
				if (!level.isClientSide()) {
					level.levelEvent((Player)null, 1009, blockpos, 0);
				}

				CampfireBlock.dowse(p_43119_.getPlayer(), level, blockpos, blockstate);
				blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false));
			}

			if (blockstate2 != null) {
				if (!level.isClientSide) {
					level.setBlock(blockpos, blockstate2, 11);
				}

				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		}
	}
}
