package andronomos.androtech.block.pad.mobkillingpad;

import andronomos.androtech.AndroTech;
import andronomos.androtech.ModEnergyStorage;
import andronomos.androtech.block.machine.MachineBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.RadiusUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class MobKillingPadBlockEntity extends MachineBlockEntity implements MenuProvider {
    public static final int PAD_SLOTS = 1;

    public MobKillingPadBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOB_KILLING_PAD.get(), pos, state);
    }

    @Override
    protected ItemStackHandler createInventoryItemHandler() {
        return new ItemStackHandler(PAD_SLOTS) {
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
                return stack.getItem() instanceof SwordItem;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    protected ModEnergyStorage createEnergyHandler() {
        return null;
    }

    public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MobKillingPadBlockEntity mobKillingPadBlockEntity) {
        List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, getWorkArea());

        ItemStack sword = this.itemHandler.getStackInSlot(0);

        if(!sword.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (entity == null || !(entity instanceof LivingEntity) || entity instanceof Player) continue;
                LivingEntity mob = (LivingEntity)entity;
                //if(mob.getHealth() > 1.0f) mob.setHealth(1.0f);
                FakePlayer fp = FakePlayerFactory.get(level, AndroTech.PROFILE);
                fp.setItemInHand(InteractionHand.MAIN_HAND, sword);
                fp.setSilent(true);
                fp.attack(entity);
                mob.setLastHurtByMob(null);
                sword.setDamageValue(0); //temporary method for keeping the sword from breaking
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MobKillingPad.DISPLAY_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MobKillingPadMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public AABB getWorkArea() {
        return RadiusUtils.threeByThreeByThreeFromTop(getBlockPos());
    }
}
