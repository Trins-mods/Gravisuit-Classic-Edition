package trinsdar.gravisuit.util.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import ic2.api.classic.addon.misc.IOverrideObject;
import ic2.bobIntigration.core.BaublesModul;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2ItemLang;
import ic2.core.util.misc.ModulLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import trinsdar.gravisuit.items.ic2override.baubles.ItemBaublesCompactedElectricJetpack2;
import trinsdar.gravisuit.items.ic2override.baubles.ItemBaublesCompactedNuclearJetpack2;

import java.util.Map;

public class BaublesLoader {
    public static void preInit(FMLPreInitializationEvent fmlPreInitializationEvent, Map<String, IOverrideObject> map){
        map.put(getID(Ic2ItemLang.jetpackCompactElectric), new ModulLoader.ItemOverride(new ItemBaublesCompactedElectricJetpack2()));
        map.put(getID(Ic2ItemLang.jetpackCompactNuclear), new ModulLoader.ItemOverride(new ItemBaublesCompactedNuclearJetpack2()));
    }

    private static String getID(LocaleComp comp) {
        return comp.getUnlocalized().replace("item.", "");
    }

    public static ItemStack getBaublesChestSlot(EntityPlayer player){
        IHasInventory inv = BaublesModul.getBaublesInventory(player);
        return inv.getStackInSlot(5);
    }
}
