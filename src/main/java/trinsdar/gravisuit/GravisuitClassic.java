package trinsdar.gravisuit;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.proxy.CommonProxy;

@Mod(modid = GravisuitClassic.MODID, name = GravisuitClassic.MODNAME, version = GravisuitClassic.MODVERSION, dependencies = GravisuitClassic.DEPENDS)
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String MODNAME = "Gravisuit Classic Edition";
    public static final String MODVERSION = "@VERSION@";
    public static final String DEPENDS ="required-after:ic2;required-after:ic2-classic-spmod";

    @SidedProxy(clientSide = "trinsdar.gravisuit.proxy.ClientProxy", serverSide = "trinsdar.gravisuit.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GravisuitClassic instance;

    public static Logger logger;

    @Mod.EventHandler
    public synchronized void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
