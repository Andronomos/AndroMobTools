package andronomos.androtech.item;

import andronomos.androtech.inventory.CropHarvesterContainer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

//public class BackpackItem extends Item {
//	public static final String SCREEN_BACKPACK = "screen.androtech.backpack";
//
//	public BackpackItem(Properties properties) {
//		super(properties);
//	}
//
//	@Override
//	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//		if(!level.isClientSide()) {
//			MenuProvider containerProvider = new MenuProvider() {
//				@Override
//				public TextComponent getDisplayName() {
//					return new TextComponent(SCREEN_BACKPACK);
//				}
//
//				@Override
//				public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
//					return new CropHarvesterContainer(windowId, pos, inventory, player);
//				}
//			};
//			NetworkHooks.openGui((ServerPlayer) player, containerProvider, entity.getBlockPos());
//		}
//
//
//
//
//
//		return super.use(level, player, hand);
//	}
//}
