package trinsdar.gravisuit.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosUtil {
    public static ItemStack getCuriosItem(Item filter, Player player){
        return CuriosApi.getCuriosHelper().findFirstCurio(player, filter).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }
}
