package andronomos.androtech.item;

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
//
//		return super.use(level, player, hand);
//	}
//}
