package andronomos.androtech.item;

import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectNullifierItem extends AbstractActivatableItem {
    public static final int NULLIFIER_DURABILITY = 1000; //will last for 1000 effect removals

    private final MobEffect effect;

    public EffectNullifierItem(Properties properties, MobEffect effect, boolean takeDamage, boolean isRepairable) {
        super(properties, takeDamage, isRepairable);
        this.effect = effect;
    }

    public EffectNullifierItem(Properties properties, MobEffect effect) {
        super(properties.setNoRepair(), true, true);
        this.effect = effect;
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
                doDamage(stack, living, false);
            }
        }
    }

    @Override
    public void activate(ItemStack stack, Player player) {
        super.activate(stack, player);
        nullifyEffect(stack, player);
    }
}
