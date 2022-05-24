package trinsdar.gravisuit.proxy;

public class CommonProxy {

    /*public void preInit(FMLPreInitializationEvent e) {
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
    }*/
}
