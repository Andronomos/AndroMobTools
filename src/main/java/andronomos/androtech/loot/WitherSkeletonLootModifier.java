package andronomos.androtech.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class WitherSkeletonLootModifier extends LootModifier {
    private final Item itemReward;

    protected WitherSkeletonLootModifier(LootItemCondition[] conditionsIn, Item reward) {
        super(conditionsIn);
        itemReward = reward;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(itemReward, 1));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<WitherSkeletonLootModifier> {
        @Override
        public WitherSkeletonLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item bone = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "item")));
            return new WitherSkeletonLootModifier(ailootcondition, bone);
        }

        @Override
        public JsonObject write(WitherSkeletonLootModifier instance) {
            return null;
        }
    }
}
