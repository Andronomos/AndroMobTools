package andronomos.androtech.network.packet;

import andronomos.androtech.item.DnaUnitItem;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.SpawnerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSpawnerMagicLeadDrop {
	private final BlockPos pos;

	public SyncSpawnerMagicLeadDrop(BlockPos pos) {
		this.pos = pos;
	}

	public static void encode(SyncSpawnerMagicLeadDrop msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
	}

	public static SyncSpawnerMagicLeadDrop decode(FriendlyByteBuf buf) {
		BlockPos pos = new BlockPos(buf.readBlockPos());

		return new SyncSpawnerMagicLeadDrop(pos);
	}

	public static void handle(SyncSpawnerMagicLeadDrop msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Level level = ctx.get().getSender().level;

			if(level == null) return;

			BlockState blockstate = level.getBlockState(msg.pos);
			SpawnerBlockEntity spawner = (SpawnerBlockEntity)level.getBlockEntity(msg.pos);
			BaseSpawner logic = spawner.getSpawner();

			String entityString = SpawnerUtil.getEntityString(logic);

			if(entityString == "")
				return;

			CompoundTag tag = new CompoundTag();
			tag.putString("entity", entityString);
			ItemStackUtil.drop(level, msg.pos, DnaUnitItem.create(level, msg.pos, tag, entityString));

			// Replace the entity inside the spawner with default entity
			logic.setEntityId(EntityType.AREA_EFFECT_CLOUD);
			spawner.setChanged();
			level.sendBlockUpdated(msg.pos, blockstate, blockstate, 3);
		});

		ctx.get().setPacketHandled(true);
	}
}
