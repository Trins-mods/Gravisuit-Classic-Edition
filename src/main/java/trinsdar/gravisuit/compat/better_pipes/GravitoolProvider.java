package trinsdar.gravisuit.compat.better_pipes;

import flyingperson.BetterPipes.BPConfig;
import flyingperson.BetterPipes.compat.wrench.IWrenchProvider;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;

public class GravitoolProvider implements IWrenchProvider {
    @Override
    public boolean enable() {
        return BPConfig.wrenchCompat.ic2wrench;
    }

    @Override
    public boolean isAcceptable(ItemStack item){
        if (item.getItem() instanceof ItemToolGravitool){
            return item.getMetadata() == 0;
        }
        return item.getItem() instanceof ItemToolWrench;
    }

    @Override
    public boolean canBeUsed(ItemStack item, EntityPlayer player){
        if (item.getItem() instanceof ItemToolGravitool){
            return ElectricItem.manager.canUse(item, 100) && item.getMetadata() == 0;
        }
        if (item.getItem() instanceof IElectricItem){
            return ElectricItem.manager.canUse(item, 50);
        }
        return true;
    }

    @Override
    public void use(ItemStack item, EntityPlayer player){
        if (item.getItem() instanceof ItemToolGravitool){
            ElectricItem.manager.use(item, 100, player);
        } else if (item.getItem() instanceof IElectricItem){
            ElectricItem.manager.use(item, 50, player);
        } else {
            item.damageItem(1, player);
        }
    }
}
