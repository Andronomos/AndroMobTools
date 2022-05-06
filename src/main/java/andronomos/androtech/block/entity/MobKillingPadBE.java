package andronomos.androtech.block.entity;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.base.AbstractTickingMachineEntity;
import andronomos.androtech.block.entity.base.BaseContainerBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.EnchantmentUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class MobKillingPadBE extends AbstractTickingMachineEntity {
    private final GameProfile PROFILE = new GameProfile(UUID.randomUUID(), "[AndroTech]");

    public MobKillingPadBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOB_KILLING_PAD.get(), pos, state);
    }

    @Override
    protected ItemStackHandler createItemHandler() {
        return new ItemStackHandler(2) {

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
                return stack.getItem() == Items.ENCHANTED_BOOK;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    public void serverTick(ServerLevel level, BlockPos pos, BlockState state, MobKillingPadBE mobKillingPadBE) {
        List<LivingEntity> list = getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), getBlockPos().getX() + 1D, getBlockPos().getY() + 1D, getBlockPos().getZ() + 1D).inflate(0.0625D, 0.0625D, 0.0625D));

        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity == null || !(entity instanceof LivingEntity) || entity instanceof Player) continue;
            LivingEntity mob = (LivingEntity)entity;
            if(mob.getHealth() > 1.0f) mob.setHealth(1.0f);
            FakePlayer fp = FakePlayerFactory.get((ServerLevel) level, PROFILE);
            ItemStack sword = new ItemStack(ModItems.FAKE_SWORD.get(), 1);
            if(hasUpgrade(Enchantments.MOB_LOOTING)) {
                sword.enchant(Enchantments.MOB_LOOTING, Const.EnchantmentLevel.III);
            }
            if(hasUpgrade(Enchantments.FIRE_ASPECT)) {
                sword.enchant(Enchantments.FIRE_ASPECT, Const.EnchantmentLevel.II);
            }
            fp.setItemInHand(InteractionHand.MAIN_HAND, sword);
            fp.attack(entity);
            mob.setLastHurtByMob(null);
        }
    }

    private boolean hasUpgrade( Enchantment enchantment) {
        for (int i = 0; i < this.inputItems.getSlots(); ++i) {
            ItemStack stack = inputItems.getStackInSlot(i);

            if(!stack.isEmpty()) {
                if(stack.getItem() == Items.ENCHANTED_BOOK) {
                    return EnchantmentUtil.hasEnchantment(enchantment, stack);
                }
            }
        }

        return false;
    }
}
