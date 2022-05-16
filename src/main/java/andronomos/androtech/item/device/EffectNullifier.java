package andronomos.androtech.item.device;

import andronomos.androtech.Const;
import andronomos.androtech.item.device.base.AbstractTickingDevice;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectNullifier extends AbstractTickingDevice {
    private final MobEffect effect;

    public EffectNullifier(Properties properties, MobEffect effect, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
        this.effect = effect;
        this.tickDelay = Const.TicksInSeconds.TWOSECONDS;
    }

    public EffectNullifier(Properties properties, MobEffect effect) {
        super(properties.setNoRepair(), false, false);
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

        if(takeDamage) {
            if(isBroken(stack)) {
                deactivate(stack, (Player)entity);
                return;
            }
        }

        Player player = (Player) entity;
        nullifyEffect(stack, player);
    }

    private void nullifyEffect(ItemStack stack, LivingEntity living) {
        if(living.hasEffect(this.effect)) {
            living.removeEffect(this.effect);
            if(takeDamage) {
                doDamage(stack, living, 1,false);
            }
        }
    }

    @Override
    public void activate(ItemStack stack, Player player) {
        super.activate(stack, player);
        nullifyEffect(stack, player);
    }
}
