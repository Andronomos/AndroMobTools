package andronomos.androtech.item.device.base;

import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class AbstractDevice extends Item {
	public static int DURABILITY = 2031;
	public int range = 20;
	public boolean takeDamage;

	public AbstractDevice(Properties properties) {
		super(properties.setNoRepair());
		this.takeDamage = false;
	}

	public AbstractDevice(Properties properties, boolean takeDamage) {
		super(properties);
		this.takeDamage = takeDamage;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return DURABILITY;
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		//minX minY minZ maxX maxY maxZ
		return new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
	}

	public boolean isBroken(ItemStack stack) {
		if(takeDamage) {
			return ItemStackUtil.isBroken(stack);
		}

		return false;
	}
}
