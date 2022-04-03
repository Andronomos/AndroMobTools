package andronomos.andromobtools.event;

import andronomos.andromobtools.item.MobStorageCellItem;
import andronomos.andromobtools.network.MobToolsPacketHandler;
import andronomos.andromobtools.network.packet.SyncSpawnerMagicLeadDrop;
import andronomos.andromobtools.util.EnchantmentUtils;
import andronomos.andromobtools.util.ItemStackUtil;
import andronomos.andromobtools.util.SpawnerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpawnerEventHandler {
	private EntityType<?> defaultEntityType = EntityType.AREA_EFFECT_CLOUD;

	/**
	 * 	Prevent XP drop when spawner is destroyed with silk touch and return Spawner Block
	 */
	@SubscribeEvent
	public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
		Block brokenBlock = event.getState().getBlock();

		if(brokenBlock == Blocks.SPAWNER) {
			ItemStack heldStack = event.getPlayer().getMainHandItem();

			if(!heldStack.canPerformAction(ToolActions.PICKAXE_DIG))
				return;

			if(!EnchantmentUtils.hasEnchantment(Enchantments.SILK_TOUCH, heldStack))
				return;

			//BlockEntity entity = event.getWorld().getBlockEntity(event.getPos());

			//if(!(entity instanceof SpawnerBlockEntity))
			//	return;

			event.setExpToDrop(0);

			// Return Spawner Block
			//ItemStack itemStack = new ItemStack(Blocks.SPAWNER.asItem());
			//entity.saveToItem(itemStack);

			ItemEntity spawnerdrop = new ItemEntity(
					(Level)event.getWorld(),
					event.getPos().getX(),
					event.getPos().getY(),
					event.getPos().getZ(),
					new ItemStack(Blocks.SPAWNER.asItem())
			);

			event.getWorld().addFreshEntity(spawnerdrop);
			dropMagicLead(event.getPos(), (Level)event.getWorld());
		}
	}

	/**
	 * 	Used to replace entity in spawner when block placed down by player
	 */
	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {

		// Leave if we did not place down a spawner
		if(event.getState().getBlock() != Blocks.SPAWNER || !(event.getEntity() instanceof Player))
			return;

		Level world = (Level) event.getWorld();
		BlockPos pos = event.getPos();

		SpawnerBlockEntity tileentity = (SpawnerBlockEntity)world.getBlockEntity(pos);
		tileentity.getSpawner().setEntityId(defaultEntityType);

		tileentity.setChanged();
		world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}

	/**
	 * 	Event when player interacts with block.
	 * 	Enables so that the player can right click a spawner to get its egg.
	 */
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		Item item = event.getItemStack().getItem();

		if(item instanceof BlockItem || event.getHand() == InteractionHand.OFF_HAND)
			return;

		Level level = event.getWorld();
		BlockPos blockpos = event.getPos();

		if(level.getBlockState(blockpos).getBlock() != Blocks.SPAWNER)
			return;

		if(!level.isClientSide || event.getPlayer().isSpectator())
			return;

		MobToolsPacketHandler.INSTANCE.sendToServer(new SyncSpawnerMagicLeadDrop(blockpos));
	}

	private void dropMagicLead(BlockPos pos, Level level) {
		BlockState blockstate = level.getBlockState(pos);
		SpawnerBlockEntity spawner = (SpawnerBlockEntity)level.getBlockEntity(pos);
		BaseSpawner logic = spawner.getSpawner();

		String entityString = SpawnerUtils.getEntityString(logic);

		if(entityString == "")
			return;

		CompoundTag tag = new CompoundTag();
		tag.putString("entity", entityString);
		ItemStackUtil.drop(level, pos, MobStorageCellItem.create(level, pos, tag, entityString));

		// Replace the entity inside the spawner with default entity
		logic.setEntityId(EntityType.AREA_EFFECT_CLOUD);
		spawner.setChanged();
		level.sendBlockUpdated(pos, blockstate, blockstate, 3);
	}
}
