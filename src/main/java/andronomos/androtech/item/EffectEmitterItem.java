package andronomos.androtech.item;

import andronomos.androtech.Const;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.io.IOException;

public class EffectEmitterItem extends AbstractActivatableItem {
    private final MobEffect effect;
    private final int amplifier;
    private boolean ignoreDamage = false;
    private final int tickDelay = Const.TicksInSeconds.FIVESECONDS;
    private int tickCounter = 0;

    public EffectEmitterItem(Properties properties, MobEffect effect, int amplifier) {
        super(properties.setNoRepair());
        this.effect = effect;
        this.amplifier = amplifier;
    }

    public InteractionResultHolder use(Level worldIn, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!worldIn.isClientSide) {
            if(!isActivated(stack) && canUse(stack)) {
                this.setState(stack, 1);
                if (!player.hasEffect(this.effect)) {
                    //effect, duration, amplifier, ambient, showParticles
                    player.addEffect(new MobEffectInstance(this.effect, 100000, this.amplifier, false, false));
                    if(!ignoreDamage) ItemStackUtil.damageItem(player, stack, 1);
                }
            } else {
                this.setState(stack, 0);
                if (player.hasEffect(this.effect)) {
                    player.removeEffect(this.effect);
                }
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if(level.isClientSide || !(entity instanceof Player) || !isActivated(stack)) return;

        if(this.tickCounter == this.tickDelay) {
            ItemStackUtil.damageItem((Player)entity, stack, 1);
            this.tickCounter = 0;
        }

        this.tickCounter++;
    }

}
