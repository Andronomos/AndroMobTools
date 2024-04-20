package andronomos.androtech.block;

import andronomos.androtech.base.MachineBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeleportInhibitorBlock extends MachineBlock {
	public static final String DISPLAY_NAME = "screen.androtech.teleport_inhibitor";
	public static final String TOOLTIP = "block.androtech.teleport_inhibitor.tooltip";

	public TeleportInhibitorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
