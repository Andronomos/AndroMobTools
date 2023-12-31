package andronomos.androtech.block.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

import java.util.Hashtable;
import java.util.List;

public class ATBlock extends Block {
	public Hashtable<String, String> textures = new Hashtable<>();

	public ATBlock(Properties properties) {
		super(properties);
	}

	public void setTexture(String key, String value) {
		textures.put(key, value);
	}

	@Override
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
