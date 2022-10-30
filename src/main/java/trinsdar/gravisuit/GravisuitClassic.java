package trinsdar.gravisuit;

import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.proxy.CommonProxy;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitKeys;
import trinsdar.gravisuit.util.GravisuitRecipes;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.render.GraviSuitOverlay;

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
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GravisuitConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GravisuitConfig.COMMON_SPEC);
        if (!FMLEnvironment.production){
            System.setProperty("ic2workspace", "true");
        }
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event){
        AdvRecipeRegistry.INSTANCE.registerListener(GravisuitRecipes::loadRecipes);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event){
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerKeys(final RegisterKeyMappingsEvent evt) {
        evt.register(GravisuitKeys.G_KEY);
    }

    @SubscribeEvent
    public void onRegisterItem(RegisterEvent event){
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)){
            Registry.init();
            REGISTRY.forEach((r, i) -> event.register(ForgeRegistries.Keys.ITEMS, r, () -> i));
        }
    }

    @SubscribeEvent
    public void loadComplete(FMLLoadCompleteEvent e) {
        MinecraftForge.EVENT_BUS.register(new GraviSuitOverlay(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer()));
    }
}
