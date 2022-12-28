package andronomos.androtech.item.Module;

import andronomos.androtech.item.base.AbstractDevice;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ChatUtils;
import andronomos.androtech.util.ItemStackUtils;
import andronomos.androtech.util.NBTUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockGPSModule extends AbstractDevice {
	public static final String TOOLTIP_BLOCK_GPS_MODULE = "tooltip.androtech.block_gps_module_location";
	public static final String TOOLTIP_BLOCK_GPS_MODULE_X = "tooltip.androtech.block_gps_module_x";
	public static final String TOOLTIP_BLOCK_GPS_MODULE_Y = "tooltip.androtech.block_gps_module_y";
	public static final String TOOLTIP_BLOCK_GPS_MODULE_Z = "tooltip.androtech.block_gps_module_z";
	public static final String BLOCK_GPS_MODULE_SAVED = "item.androtech.block_gps_module.saved";

	public BlockGPSModule(Properties properties) {
		super(properties);
		this.DURABILITY = 5;
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return ItemStackUtils.getBlockPos(stack) == null ? 64 : 1;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		ItemStack held = player.getItemInHand(hand);

		int numHeld = held.getCount();

		if(numHeld == 1) {
			held = recordPos(held, pos);
		} else {
			recordPos(pos, player);
			held.shrink(1);
		}

		player.swing(hand);
		ChatUtils.sendStatusMessage(player, Component.translatable(BLOCK_GPS_MODULE_SAVED) + ChatUtils.blockPosToString(pos));
		return InteractionResult.SUCCESS;
	}

	private void recordPos(BlockPos pos, Player player) {
		ItemStack drop = new ItemStack(ModItems.BLOCK_GPS_MODULE.get());
		setBlockPos(drop, pos);
		if(!player.addItem(drop)) ItemStackUtils.drop(player.level, player.blockPosition(), drop);
	}

	private ItemStack recordPos(ItemStack stack, BlockPos pos) {
		setBlockPos(stack, pos);
		return stack;
	}

	private void setBlockPos(ItemStack stack, BlockPos pos) {
		if (pos == null || stack.isEmpty()) {
			return;
		}

		NBTUtils.setIntVal(stack, "xpos", pos.getX());
		NBTUtils.setIntVal(stack, "ypos", pos.getY());
		NBTUtils.setIntVal(stack, "zpos", pos.getZ());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		BlockPos pos = ItemStackUtils.getBlockPos(stack);

		if(pos != null) {
			tooltip.add(Component.translatable(TOOLTIP_BLOCK_GPS_MODULE).withStyle(ChatFormatting.GRAY));
			String xCoord = String.format("%s%s", Component.translatable(TOOLTIP_BLOCK_GPS_MODULE_X), pos.getX());
			String yCoord = String.format("%s%s", Component.translatable(TOOLTIP_BLOCK_GPS_MODULE_Y), pos.getY());
			String zCoord = String.format("%s%s", Component.translatable(TOOLTIP_BLOCK_GPS_MODULE_Z), pos.getZ());
			String coords = String.format("%s %s %s", xCoord, yCoord, zCoord);
			tooltip.add(Component.literal(coords).withStyle(ChatFormatting.BLUE));
		}
	}
}
