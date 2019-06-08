package trinsdar.gravisuit;

import ic2.api.classic.addon.IC2Plugin;
import ic2.api.classic.addon.PluginBase;
import ic2.api.classic.addon.misc.IOverrideObject;
import ic2.core.IC2;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2ItemLang;
import ic2.core.util.misc.ModulLoader;
import ic2.core.util.obj.plugins.IBaublesPlugin;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import trinsdar.gravisuit.items.ic2override.ItemArmorCompactedElectricJetpack2;
import trinsdar.gravisuit.items.ic2override.ItemArmorCompactedNuclearJetpack2;
import trinsdar.gravisuit.items.ic2override.ItemArmorQuantumJetplate2;
import trinsdar.gravisuit.items.ic2override.ItemArmorQuantumNuclearJetplate2;
import trinsdar.gravisuit.items.ic2override.baubles.ItemBaublesCompactedElectricJetpack2;
import trinsdar.gravisuit.items.ic2override.baubles.ItemBaublesCompactedNuclearJetpack2;

import java.util.Map;

@IC2Plugin(id = "gravisuit_override", name = "GravisuitClassicOverride", version = GravisuitClassic.MODVERSION)
public class GravisuitClassicOverridePlugin extends PluginBase {
    @Override
    public boolean canLoad(Side side) {
        return true;
    }

    @Override
    public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent, Map<String, IOverrideObject> map)
    {
        IBaublesPlugin plugin = IC2.loader.getPlugin("baubles", IBaublesPlugin.class);
        map.put(getID(Ic2ItemLang.quantumJetplate), new ModulLoader.ItemOverride(new ItemArmorQuantumJetplate2()));
        map.put(getID(Ic2ItemLang.quantumNuclearJetplate), new ModulLoader.ItemOverride(new ItemArmorQuantumNuclearJetplate2()));
        if (plugin != null){
            map.put(getID(Ic2ItemLang.jetpackCompactElectric), new ModulLoader.ItemOverride(new ItemBaublesCompactedElectricJetpack2()));
            map.put(getID(Ic2ItemLang.jetpackCompactNuclear), new ModulLoader.ItemOverride(new ItemBaublesCompactedNuclearJetpack2()));
        }else {
            map.put(getID(Ic2ItemLang.jetpackCompactElectric), new ModulLoader.ItemOverride(new ItemArmorCompactedElectricJetpack2()));
            map.put(getID(Ic2ItemLang.jetpackCompactNuclear), new ModulLoader.ItemOverride(new ItemArmorCompactedNuclearJetpack2()));
        }
    }

    private static String getID(LocaleComp comp) {
        return comp.getUnlocalized().replace("item.", "");
    }
}
