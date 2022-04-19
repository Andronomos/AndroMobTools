package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.item.MobStorageCellItem;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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

public class MobClonerBE extends BaseContainerBlockEntity implements TickingBlockEntity {
	private double spin;
	private double oSpin;
	private int maxNearbyEntities = 6;
	private int requiredPlayerRange = 64;
	private int spawnCount = 4;
	private int spawnRange = 4;
	private int tickDelay = Const.TicksInSeconds.FIVESECONDS;
	private int tickCounter = 0;

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

		ItemStack stack = inputItems.getStackInSlot(0);

		if(stack == null
				|| stack.isEmpty()
				|| !(stack.getItem() instanceof MobStorageCellItem)
				|| !ItemStackUtil.containsEntity(stack)) return;

		if(shouldTick()) {
			for(int i = 0; i < spawnCount; ++i) {
				Entity entity = ItemStackUtil.getEntityFromStack(stack, this.level, true);
				entity.setUUID(Mth.createInsecureUUID());
				double d0 = (double)pos.getX() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)this.spawnRange + 0.5D;
				double d1 = pos.getY() + this.level.random.nextInt(3) - 1;
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

	public boolean shouldTick() {
		if (tickCounter < tickDelay) {
			tickCounter++;
			return false;
		} else {
			tickCounter = 0;
		}
		return true;
	}

	private boolean isNearPlayer(Level level, BlockPos blockPos) {
		return level.hasNearbyAlivePlayer((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, (double)this.requiredPlayerRange);
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Nonnull
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(1) {
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return stack.getItem() instanceof MobStorageCellItem;
			}
		};
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("Inventory", inputItems.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Inventory")) {
			inputItems.deserializeNBT(tag.getCompound("Inventory"));
		}
	}


}
