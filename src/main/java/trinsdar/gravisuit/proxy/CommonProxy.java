package trinsdar.gravisuit.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import trinsdar.gravisuit.items.armor.ItemArmorGravisuit;
import trinsdar.gravisuit.util.GravisuitRecipes;
import trinsdar.gravisuit.util.Registry;

public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        //File directory = e.getModConfigurationDirectory();
        //config = new Configuration(new File(directory.getPath(), "ic2/advancedsolars.cfg"));
        //Config.readConfig();
        Registry.init();
        MinecraftForge.EVENT_BUS.register(new ItemArmorGravisuit.GravisuitJetpack(Registry.gravisuit));
    }

    public void init(FMLInitializationEvent e) {
        GravisuitRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
        // temporarily empty post init method
    }
}
