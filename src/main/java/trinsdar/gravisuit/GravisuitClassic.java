package trinsdar.gravisuit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.compat.better_pipes.BetterPipesInit;
import trinsdar.gravisuit.proxy.CommonProxy;

@Mod(modid = GravisuitClassic.MODID, name = GravisuitClassic.MODNAME, version = GravisuitClassic.MODVERSION, dependencies = GravisuitClassic.DEPENDS)
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String MODNAME = "Gravisuit Classic Edition";
    public static final String MODVERSION = "@VERSION@";
    public static final String DEPENDS ="required-after:ic2;required-after:ic2-classic-spmod;before:gtc_expansion@[0.0.6,);before:ic2c_extras@[1.4.8,);after:betterpipes@[0.14,)";
    public static final String networkChannelName = MODID;

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = "trinsdar.gravisuit.proxy.ClientProxy", serverSide = "trinsdar.gravisuit.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GravisuitClassic instance;

    public static Logger logger;

    @Mod.EventHandler
    public synchronized void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
        {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        if (Loader.isModLoaded("betterpipes")){
            BetterPipesInit.init();
        }
    }
}
