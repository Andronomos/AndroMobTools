package andronomos.androtech.block.animalfarmer;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Const;
import andronomos.androtech.block.TickingBE;
import andronomos.androtech.block.mobcloner.MobCloner;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class AnimalFarmerBE extends TickingBE {
	public AnimalFarmerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ANIMAL_FARMER.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(AnimalFarmer.SLOTS) {
			@Override
			public int getSlotLimit(int slotId) {
				return 64;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slotId, @Nonnull ItemStack stack) {
				return true;
			}
		};
	}

	public AABB getWorkArea(BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return new AABB(x - 4, y + 2, z - 4, x + 4, y + 2, z + 4);
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		//if(!state.getValue(AnimalFarmer.POWERED)) return;
		//
		//double d0 = (double)pos.getX() + level.random.nextDouble();
		//double d1 = (double)pos.getY() + level.random.nextDouble();
		//double d2 = (double)pos.getZ() + level.random.nextDouble();
		//level.addParticle(ParticleTypes., d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(state.getValue(MobCloner.POWERED)) {
			if(!shouldTick()) return;

			AABB workArea = getWorkArea(pos);

			List<LivingEntity> entities = getLevel().getEntitiesOfClass(LivingEntity.class, workArea, EntitySelector.ENTITY_STILL_ALIVE);

			for (LivingEntity entity : entities) {
				AndroTech.LOGGER.info("AnimalFarmerBE#serverTick | entity = {}", entity);
			}
		}
	}
}
