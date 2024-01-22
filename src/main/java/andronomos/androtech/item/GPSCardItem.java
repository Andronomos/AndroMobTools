package andronomos.androtech.item;

import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.util.ChatHelper;
import andronomos.androtech.util.ItemStackHelper;
import andronomos.androtech.util.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GPSCardItem extends MultiStateItem {
	public static final String TOOLTIP_GPS_CARD = "tooltip.androtech.gps_card_location";
	public static final String TOOLTIP_GPS_CARD_COORDS = "tooltip.androtech.gps_card_coords";
	public static final String GPS_CARD_SAVED = "item.androtech.gps_card.saved";

	public GPSCardItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return ItemStackHelper.getBlockPos(stack) == null ? 64 : 1;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		BlockPos pos = ItemStackHelper.getBlockPos(stack);
		if(pos != null) {
			tooltip.add(Component.translatable(TOOLTIP_GPS_CARD).withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.translatable(GPS_CARD_SAVED, ChatHelper.blockPosToString(pos)).withStyle(ChatFormatting.BLUE));
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		ItemStack held = player.getItemInHand(hand);
		int numHeld = held.getCount();
		if(numHeld == 1) {
			held = recordPos(held, pos, player);
		} else {
			recordPos(pos, player);
			held.shrink(1);
		}
		player.swing(hand);
		ChatHelper.sendStatusMessage(player, Component.translatable(GPS_CARD_SAVED, ChatHelper.blockPosToString(pos)));
		return InteractionResult.SUCCESS;
	}

	private void recordPos(BlockPos pos, Player player) {
		ItemStack drop = new ItemStack(ItemRegistry.GPS_CARD.get());
		setBlockPos(drop, pos, player);
		if(!player.addItem(drop)) ItemStackHelper.drop(player.level(), player.blockPosition(), drop);
	}

	private ItemStack recordPos(ItemStack stack, BlockPos pos, Player player) {
		setBlockPos(stack, pos, player);
		return stack;
	}

	private void setBlockPos(ItemStack stack, BlockPos pos, Player player) {
		if (pos == null || stack.isEmpty()) {
			return;
		}
		NBTHelper.setIntVal(stack, "xpos", pos.getX());
		NBTHelper.setIntVal(stack, "ypos", pos.getY());
		NBTHelper.setIntVal(stack, "zpos", pos.getZ());
	}
}
