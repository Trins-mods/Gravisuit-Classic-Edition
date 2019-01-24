package trinsdar.gravisuit.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        //File directory = e.getModConfigurationDirectory();
        //config = new Configuration(new File(directory.getPath(), "ic2/advancedsolars.cfg"));
        //Config.readConfig();
        //MinecraftForge.EVENT_BUS.register(Registry.class);
    }

    public void init(FMLInitializationEvent e) {
        //AdvancedSolarsRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
        // temporarily empty post init method
    }
}
