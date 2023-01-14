package andronomos.androtech.block.machine.mobcloner;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineSlotItemStackHandler;
import andronomos.androtech.block.machine.MachineTickingBlockEntity;
import andronomos.androtech.item.Module.MobStasisModule;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ItemStackUtils;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobClonerBlockEntity extends MachineTickingBlockEntity implements MenuProvider {
	private double spin;
	private int requiredPlayerRange = 64;
	private int spawnCount = 1;
	private double spawnRange = 4;

	public static final int SLOTS = 1;

	public MobClonerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.MOB_CLONER.get(), pos, state);
		tickDelay = Const.TicksInSeconds.FIVE;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(MobCloner.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player pPlayer) {
		return new MobClonerMenu(id, inventory, this);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new MachineSlotItemStackHandler(this, ModItems.MOB_STASIS_MODULE.get(), SLOTS) {
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				if(stack.getItem() == this.allowedItem) {
					return ItemStackUtils.hasEntityTag(stack);
				}
				return false;
			}
		};
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		this.spin = (this.spin + (double)(1000.0F / (20 + 200.0F))) % 360.0D;
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		if(shouldTick()) {
			for(int slotIndex = 0; slotIndex < itemHandler.getSlots(); slotIndex++) {
				ItemStack clonerModule = itemHandler.getStackInSlot(slotIndex);

				if(clonerModule == null
						|| clonerModule.isEmpty()
						|| !(clonerModule.getItem() instanceof MobStasisModule)
						|| !ItemStackUtils.hasEntityTag(clonerModule)) continue;

				for(int i = 0; i < spawnCount; ++i) {
					Entity entity = ItemStackUtils.createEntity(clonerModule, level, true);
					//entity.setSilent(true);
					entity.setDeltaMovement(0, entity.getDeltaMovement().y(), 0);
					entity.setUUID(Mth.createInsecureUUID());
					double d0 = pos.getX() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * this.spawnRange;
					double d1 = pos.getY() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * this.spawnRange;
					double d2 = pos.getZ() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * this.spawnRange;

					if(this.level.noCollision(entity.getType().getAABB(d0, d1, d2))) {
						AABB area = getWorkArea();
						List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, area.inflate(.5), EntitySelector.ENTITY_STILL_ALIVE);
						if(nearbyEntities.size() > getMaxNearbyEntities()) {
							return;
						}
						entity.absMoveTo(d0, d1, d2, 0, 0);
						this.level.addFreshEntity(entity);
						if (entity instanceof Mob mob) mob.spawnAnim();
					}
				}
			}
		}
	}

	@Override
	public AABB getWorkArea() {
		return RadiusUtils.cubefromCenter(getBlockPos(), (int) this.spawnRange);
	}

	private int getMaxNearbyEntities() {
		return spawnCount * SLOTS;
	}

	public boolean shouldActivate(Level level, BlockPos pos) {
		if (!this.isNearPlayer(this.level, pos)
				|| level.hasNeighborSignal(pos)
				|| !this.getBlockState().getValue(MobCloner.POWERED))
			return false;

		return true;
	}

	private boolean isNearPlayer(Level level, BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return level.hasNearbyAlivePlayer(x, y, z, (double)this.requiredPlayerRange);
	}
}
