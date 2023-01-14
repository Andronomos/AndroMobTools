package andronomos.androtech.block.machine.itemmender;

import andronomos.androtech.Const;
import andronomos.androtech.block.machine.MachineTickingBlockEntity;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemMenderBlockEntity extends MachineTickingBlockEntity implements MenuProvider {
	public ItemMenderBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ITEM_MENDER.get(), pos, state);
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Const.INVENTORY_GENERIC_LARGE_SIZE) {
			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return ItemStackUtils.isRepairable(stack);
			}
		};
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if(shouldTick()) {
			getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandler -> {
				for (int i = 0; i < itemHandler.getSlots(); i++) {
					ItemStack itemstack = itemHandler.getStackInSlot(i);
					if (!ItemStackUtils.isRepairable(itemstack)) continue;
					itemstack.setDamageValue(itemstack.getDamageValue() - AndroTechConfig.MENDING_MODULE_REPAIR_RATE.get());
				}
			});
		}
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(ItemMender.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new ItemMenderMenu(pContainerId, pPlayerInventory, this);
	}
}
