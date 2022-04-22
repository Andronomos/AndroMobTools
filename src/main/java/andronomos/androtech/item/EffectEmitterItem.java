package andronomos.androtech.item;

import andronomos.androtech.AndroTech;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectEmitterItem extends AbstractActivatableItem {
    public static final int EMITTER_DURABILITY = 720;

    private final MobEffect effect;
    private final int amplifier;

    public EffectEmitterItem(Properties properties, MobEffect effect, int amplifier, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
        this.effect = effect;
        this.amplifier = amplifier;
    }

    public EffectEmitterItem(Properties properties, MobEffect effect, int amplifier) {
        super(properties.setNoRepair(), true, true);
        this.effect = effect;
        this.amplifier = amplifier;
    }

    public InteractionResultHolder use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!level.isClientSide) {
            if(!isActivated(stack) && !isBroken(stack)) {
                activate(stack, player);
            } else {
                deactivate(stack, player);
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

        if(this.takeDamage) {
            if(isBroken(stack)) {
                deactivate(stack, (Player)entity);
                return;
            }
        }

        if(this.tickCounter == this.tickDelay) {
            if(this.takeDamage) {
                doDamage(stack, entity);
            }

            this.tickCounter = 0;
        }

        this.tickCounter++;
    }

    @Override
    public void doDamage(ItemStack stack, Entity entity) {
        if(stack.getDamageValue() < stack.getMaxDamage()) {
            ItemStackUtil.damageItem((Player)entity, stack, 1);
        }
    }

    @Override
    public void activate(ItemStack stack, Player player) {
        this.setActivated(stack, 1);
        if (!player.hasEffect(this.effect)) {
            //effect, duration, amplifier, ambient, showParticles
            player.addEffect(new MobEffectInstance(this.effect, 6000, this.amplifier, false, false));
        }
    }

    @Override
    public void deactivate(ItemStack stack, Player player) {
        this.setActivated(stack, 0);
        if (player.hasEffect(this.effect)) {
            player.removeEffect(this.effect);
        }
    }
}
