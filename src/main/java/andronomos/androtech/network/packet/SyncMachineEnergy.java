package andronomos.androtech.network.packet;

import andronomos.androtech.block.machine.Machine;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.block.machine.MachineMenu;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerBlockEntity;
import andronomos.androtech.block.machine.cropfarmer.CropFarmerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMachineEnergy {
	private final int energy;
	private final BlockPos pos;

	public SyncMachineEnergy(int energy, BlockPos pos) {
		this.energy = energy;
		this.pos = pos;
	}

	public SyncMachineEnergy(FriendlyByteBuf buf) {
		this.energy = buf.readInt();
		this.pos = buf.readBlockPos();
	}

	public static void encode(SyncMachineEnergy msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.energy);
		buf.writeBlockPos(msg.pos);
	}

	public static SyncMachineEnergy decode(FriendlyByteBuf buf) {
		return new SyncMachineEnergy(buf.readInt(), buf.readBlockPos());
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof MachineBlockEntity blockEntity) {
				blockEntity.setEnergyLevel(energy);

				if(Minecraft.getInstance().player.containerMenu instanceof MachineMenu menu && menu.blockEntity.getBlockPos().equals(pos)) {
					blockEntity.setEnergyLevel(energy);
				}
			}
		});
		return true;
	}
}
