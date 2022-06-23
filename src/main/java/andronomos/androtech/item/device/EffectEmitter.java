package andronomos.androtech.item.device;

import andronomos.androtech.item.device.base.TickingDevice;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectEmitter extends TickingDevice {
    private final MobEffect effect;
    private final int amplifier;

    public EffectEmitter(Item.Properties properties, MobEffect effect, int amplifier) {
        super(properties.setNoRepair(), true);
        this.effect = effect;
        this.amplifier = amplifier;
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

        giveEffectToPlayer(stack, (Player) entity);
    }

    private void giveEffectToPlayer(ItemStack stack, Player player) {
        if (!player.hasEffect(this.effect)) {
            //effect, duration, amplifier, ambient, showParticles
            player.addEffect(new MobEffectInstance(this.effect, 6000, this.amplifier, false, false));
            if(takeDamage) {
                doDamage(stack, player, 1,false);
            }
        }
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
