package andronomos.androtech.item.activatableItem;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectEmitter extends AbstractActivatableItem {
    public static final int EMITTER_DURABILITY = 720;

    private final MobEffect effect;
    private final int amplifier;

    public EffectEmitter(Properties properties, MobEffect effect, int amplifier, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
        this.effect = effect;
        this.amplifier = amplifier;
    }

    public EffectEmitter(Properties properties, MobEffect effect, int amplifier) {
        super(properties.setNoRepair(), false, false);
        this.effect = effect;
        this.amplifier = amplifier;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

        if(isBroken(stack)) {
            deactivate(stack, (Player)entity);
            return;
        }

        if(tickCounter == tickDelay) {
            if(takeDamage) {
                doDamage(stack, entity, 1,false);
            }

            tickCounter = 0;
        }

        tickCounter++;
    }

    @Override
    public void activate(ItemStack stack, Player player) {
        super.activate(stack, player);
        if (!player.hasEffect(this.effect)) {
            //effect, duration, amplifier, ambient, showParticles
            player.addEffect(new MobEffectInstance(this.effect, 6000, this.amplifier, false, false));
        }
    }

    @Override
    public void deactivate(ItemStack stack, Player player) {
        super.deactivate(stack, player);
        if (player.hasEffect(this.effect)) {
            player.removeEffect(this.effect);
        }
    }
}
