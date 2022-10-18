package trinsdar.gravisuit;

import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.proxy.CommonProxy;
import trinsdar.gravisuit.util.Registry;

import static trinsdar.gravisuit.util.Registry.REGISTRY;

@Mod(GravisuitClassic.MODID)
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String networkChannelName = MODID;

   // public static SimpleNetworkWrapper network;

    public static CommonProxy proxy;

    public static Logger logger;
    public GravisuitClassic(){
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        if (!FMLEnvironment.production){
            System.setProperty("ic2workspace", "true");
        }
    }

    public void onClientSetup(FMLClientSetupEvent event){
        //IC2Textures.INSTANCE.loadRenderMaterials();
    }

    @SubscribeEvent
    public void onRegisterItem(RegisterEvent event){
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)){
            Registry.init();
            REGISTRY.forEach((r, i) -> event.register(ForgeRegistries.Keys.ITEMS, r, () -> i));
        }
    }
}
