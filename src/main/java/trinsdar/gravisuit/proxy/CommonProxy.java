package trinsdar.gravisuit.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.armor.ItemArmorGravisuit;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.util.GravisuitRecipes;
import trinsdar.gravisuit.util.Registry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Registry.initVars();
        Registry.init();
        initializeNetwork();
        MinecraftForge.EVENT_BUS.register(new ItemArmorGravisuit.GravisuitJetpack(Registry.gravisuit));
    }

    public void init(FMLInitializationEvent e) {
        GravisuitRecipes.init();
    }

    public void initializeNetwork() {
        GravisuitClassic.network = NetworkRegistry.INSTANCE.newSimpleChannel(GravisuitClassic.networkChannelName);
        GravisuitClassic.network.registerMessage(PacketRelocator.Handler.class, PacketRelocator.class, 0, Side.SERVER);
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
