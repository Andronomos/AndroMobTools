package andronomos.androtech.item.base;

import andronomos.androtech.AndroTech;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.util.ChatHelper;
import andronomos.androtech.util.ItemStackHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDeviceItem extends Item {
	public static int DURABILITY = AndroTechConfig.DEVICE_DURABILITY.get();
	public boolean hasDurability = false;

	public AbstractDeviceItem(Properties properties) {
		super(properties);
	}

	public AbstractDeviceItem(Properties properties, boolean hasDurability) {
		super(properties);
		this.hasDurability = hasDurability;
	}

	/**
	 * For debugging purposes
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		if(!level.isClientSide) {
			ItemStack stack = player.getItemInHand(hand);
			CompoundTag tag = stack.getTag();
			AndroTech.LOGGER.info(String.format("MultiStateItem#use | stack nbt: %s", tag != null ? tag.toString() : "null"));
			ChatHelper.sendStatusMessage(player, Component.literal(tag != null ? tag.toString() : "null"));
		}
		return super.use(level, player, hand);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.DEVICE_DURABILITY.get();
	}

	public void doDamage(ItemStack stack, Entity entity, int amount, boolean preventBreaking) {
		if(stack.getDamageValue() < stack.getMaxDamage()) {
			ItemStackHelper.applyDamage((Player)entity, stack, amount, preventBreaking);
		}
	}

	public boolean isBroken(ItemStack stack) {
		if(hasDurability) {
			return ItemStackHelper.isBroken(stack);
		}
		return false;
	}
}
