package andronomos.androtech.event;

import andronomos.androtech.block.TeleportInhibitorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TeleportInhibitorEvent {
	@SubscribeEvent
	public void onEntityTeleport (EntityTeleportEvent event) {
		if(event.getEntity().getCommandSenderWorld().isClientSide ||
				event instanceof EntityTeleportEvent.TeleportCommand ||
				event instanceof EntityTeleportEvent.SpreadPlayersCommand) {
			return;
		}

		if(event.getEntity() instanceof LivingEntity living) {
			//Thanks to vadis365 - Mob-Grinding-Utils for this code
			//https://github.com/vadis365/Mob-Grinding-Utils/blob/d0705ed9054216b06db4b0e8a15af4fcac21325a/MobGrindingUtils/MobGrindingUtils/src/main/java/mob_grinding_utils/events/MGUEndermanInhibitEvent.java#L4
			AABB inhibitedRange = living.getBoundingBox().inflate(9.0D, 9.0D, 9.0D);

			int minX = Mth.floor(inhibitedRange.minX);
			int maxX = Mth.floor(inhibitedRange.maxX);
			int minY = Mth.floor(inhibitedRange.minY);
			int maxY = Mth.floor(inhibitedRange.maxY);
			int minZ = Mth.floor(inhibitedRange.minZ);
			int maxZ = Mth.floor(inhibitedRange.maxZ);

			BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

			for (int x = minX; x < maxX; x++) {
				for (int y = minY; y < maxY; y++) {
					for (int z = minZ; z < maxZ; z++) {
						BlockState state = living.getCommandSenderWorld().getBlockState(mutablePos.set(x, y, z));
						if (state.getBlock() instanceof TeleportInhibitorBlock) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
}
