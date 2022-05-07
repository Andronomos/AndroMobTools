package andronomos.androtech.block.entity;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.entity.base.BaseTickingMachineEntity;
import andronomos.androtech.item.MobCloningModule;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MobClonerBE extends BaseTickingMachineEntity {
	public static final int CLONER_SLOTS = 9;

	private double spin;
	private double oSpin;
	private int maxNearbyEntities = 10;
	private int requiredPlayerRange = 64;
	private int spawnCount = 10;
	private int spawnRange = 4;

	public MobClonerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.MOB_CLONER_BE.get(), pos, state);
	}

	public boolean shouldActivate(Level level, BlockPos pos) {
		if (!this.isNearPlayer(this.level, pos)) return false;
		if(level.hasNeighborSignal(pos)) return false;
		return true;
	}

	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		this.spin = (this.spin + (double)(1000.0F / (20 + 200.0F))) % 360.0D;
	}

	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		AndroTech.LOGGER.info("MobClonerBE#serverTick | inputItems.getSlots()={}", inputItems.getSlots());

		if(shouldTick()) {
			for(int slotIndex = 0; slotIndex < inputItems.getSlots(); slotIndex++) {
				ItemStack clonerModule = inputItems.getStackInSlot(slotIndex);

				if(clonerModule == null
						|| clonerModule.isEmpty()
						|| !(clonerModule.getItem() instanceof MobCloningModule)
						|| !ItemStackUtil.containsEntity(clonerModule)) return;

				for(int i = 0; i < spawnCount; ++i) {
					Entity entity = ItemStackUtil.getEntityFromStack(clonerModule, this.level, true);
					entity.setUUID(Mth.createInsecureUUID());
					double d0 = (double)pos.getX() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)this.spawnRange + 0.5D;
					double d1 = pos.getY() - 1;
					double d2 = (double)pos.getZ() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)this.spawnRange + 0.5D;

					if(this.level.noCollision(entity.getType().getAABB(d0, d1, d2))) {
						int k = this.level.getEntitiesOfClass(entity.getClass(), (new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).inflate(this.spawnRange)).size();
						if (k >= this.maxNearbyEntities) return;
						entity.absMoveTo(d0, d1, d2, 0, 0);
						this.level.addFreshEntity(entity);
						if (entity instanceof Mob) ((Mob)entity).spawnAnim();
					}
				}
			}
		}
	}

	private boolean isNearPlayer(Level level, BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return level.hasNearbyAlivePlayer(x, y, z, (double)this.requiredPlayerRange);
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Nonnull
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(MobClonerBE.CLONER_SLOTS) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return stack.getItem() instanceof MobCloningModule;
			}
		};
	}
}
