package andronomos.andromobtools.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.List;

public class PlayerUtil {
    public static Inventory GetInventory(Player player)
    {
        return player.getInventory();
    }

    public static List<NonNullList<ItemStack>> GetAllInventories(Player player)
    {
        Inventory inventory = GetInventory(player);
        return ImmutableList.of(inventory.items, inventory.armor, inventory.offhand);
    }

    public static void setPlayerReach(Player player, int currentReach) {
        player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(currentReach);
    }
}
