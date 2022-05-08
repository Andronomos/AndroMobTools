package andronomos.androtech.item;

import andronomos.androtech.block.harvester.IHarvester;
import andronomos.androtech.util.BlockUtil;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class OreTranslocator extends Item {
	private int range = 20;

	public OreTranslocator(Properties properties) {
		super(properties);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return 2304;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(ItemStackUtil.isBroken(heldItem)) {
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

				ItemStackUtil.damageItem(player, heldItem, 1, true);
			}
		}

		return InteractionResultHolder.success(heldItem);
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		//minX minY minZ maxX maxY maxZ
		return new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
	}
}
