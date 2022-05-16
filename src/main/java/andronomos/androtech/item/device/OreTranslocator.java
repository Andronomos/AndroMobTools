package andronomos.androtech.item.device;

import andronomos.androtech.item.device.base.AbstractDevice;
import andronomos.androtech.util.BlockUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class OreTranslocator extends AbstractDevice {
	public OreTranslocator(Properties properties) {
		super(properties);
	}

	public OreTranslocator(Properties properties, boolean takeDamage, boolean isRepairable) {
		super(properties, takeDamage, isRepairable);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(isBroken(heldItem)) {
				return InteractionResultHolder.pass(heldItem);
			}

			AABB workArea = getWorkArea(player.blockPosition());

			List<BlockPos> oreInRange = BlockUtil.getOreInArea(workArea, level);

			for (BlockPos ore : oreInRange) {
				BlockState state = level.getBlockState(ore);
				Block block = state.getBlock();

				level.destroyBlock(ore, false);

				ItemStack drop = new ItemStack(block.asItem());

				boolean inserted = player.getInventory().add(drop);

				if(!inserted) {
					level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop));
				}

				ItemStackUtil.applyDamage(player, heldItem, 1, true);
			}
		}

		return InteractionResultHolder.success(heldItem);
	}
}
