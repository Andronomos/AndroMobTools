package andronomos.androtech.item;

import andronomos.androtech.item.device.base.AbstractDevice;
import andronomos.androtech.util.BlockUtil;
import andronomos.androtech.util.ChatUtil;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidRemover extends AbstractDevice {
	public static final String FLUID_REMOVER_MODE = "item.androtech.fluid_remover.mode";
	private FlowingFluid targetFluid;

	public FluidRemover(Properties properties) {
		super(properties, true);
		this.range = 4;
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level level, Player player) {
		setMode(stack, Fluids.WATER);
		super.onCraftedBy(stack, level, player);
	}

	/** We manually add the item to the creative menu so it will have a default mode **/
	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if(this.allowdedIn(tab)) {
			ItemStack stack = new ItemStack(this);
			setMode(stack, Fluids.WATER);
			items.add(stack);
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(!level.isClientSide) {
			if(player.isCrouching()) {
				setMode(heldItem, this.targetFluid == Fluids.WATER ? Fluids.LAVA : Fluids.WATER);
				ChatUtil.sendStatusMessage(player, ChatUtil.createTranslation(FLUID_REMOVER_MODE) + getFluidName());
			} else {
				if(isBroken(heldItem)) {
					return InteractionResultHolder.pass(heldItem);
				}

				removeFluid(level, player, heldItem);
			}
		}

		return InteractionResultHolder.success(heldItem);
	}

	private void setMode(ItemStack heldItem, FlowingFluid fluid) {
		this.targetFluid = fluid;
		NBTUtil.setStringVal(heldItem, "fluid", getFluidName());
	}

	private String getFluidName() {
		String fluidName = this.targetFluid.getRegistryName().getPath();
		fluidName = fluidName.substring(0, 1).toUpperCase() + fluidName.substring(1);
		return fluidName;
	}

	private void removeFluid(Level level, Player player, ItemStack heldItem) {
		AABB workArea = getWorkArea(player.blockPosition());

		for (BlockPos fluid : BlockUtil.getFluid(workArea, level, targetFluid)) {
			level.setBlockAndUpdate(fluid, Blocks.AIR.defaultBlockState());
			ItemStackUtil.applyDamage(player, heldItem, 1, true);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag tag = NBTUtil.getStackTag(stack);
		String mode = String.format("%s%s", ChatUtil.createTranslation(FLUID_REMOVER_MODE), tag.getString("fluid"));
		tooltip.add(new TextComponent(mode).withStyle(ChatFormatting.BLUE));
	}
}
