package trinsdar.gravisuit.util;

import ic2.api.addons.IModule;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.curioplugin.core.CurioPlugin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;

public class CuriosUtil {
    public static ItemStack getCuriosItem(Item filter, Player player){
        return CuriosApi.getCuriosHelper().findFirstCurio(player, filter).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }

    public static List<ItemStack> getCuriosBackItems(Player player) {
        IModule plugin = IC2.PLUGINS.getModule("curio");
        if (plugin != null) {
            IItemHandler inv = new CurioPlugin().getCurioInv(player, "back");
            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < inv.getSlots(); i++) {
                items.add(inv.getStackInSlot(i));
            }
            return items;
        }
        return List.of();
    }
}
