package andronomos.androtech.util;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;

public class LivingEntityUtil {
    public static boolean isHostile(LivingEntity entityIn) {
        if(entityIn instanceof Monster || (entityIn).getSoundSource() == SoundSource.HOSTILE)
            return true;

        return false;
    }
}
