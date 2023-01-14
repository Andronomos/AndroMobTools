package andronomos.androtech.block.machine;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

import java.util.List;

public abstract class Machine extends Block {
	public boolean useDefaultTopTexture;
	public boolean useDefaultBottomTexture;
	public boolean useDefaultSideTexture;
	public boolean useDefaultFrontTexture;
	public boolean hasMultipleStates;

	public Machine(Properties properties, boolean useDefaultSideTexture, boolean useDefaultBottomTexture,
				   boolean useDefaultTopTexture, boolean useDefaultFrontTexture, boolean hasMultipleStates) {
		super(properties);
		this.useDefaultSideTexture = useDefaultSideTexture;
		this.useDefaultBottomTexture = useDefaultBottomTexture;
		this.useDefaultTopTexture = useDefaultTopTexture;
		this.useDefaultFrontTexture = useDefaultFrontTexture;
		this.hasMultipleStates = hasMultipleStates;
	}

	@Override
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
