package trinsdar.gravisuit;

import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.proxy.CommonProxy;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitKeys;
import trinsdar.gravisuit.util.GravisuitRecipes;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.render.GraviSuitOverlay;
import trinsdar.gravisuit.util.render.PlasmaBallRenderer;

import java.sql.Ref;

import static trinsdar.gravisuit.util.Registry.REGISTRY;

@Mod(GravisuitClassic.MODID)
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String networkChannelName = MODID;

    private static final String MAIN_CHANNEL = "main_channel";
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public static SimpleChannel NETWORK;

    public static CommonProxy proxy;

    public static Logger logger;
    private int currMessageId = 0;

    public GravisuitClassic(){
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GravisuitConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GravisuitConfig.COMMON_SPEC);
        if (!FMLEnvironment.production){
            System.setProperty("ic2workspace", "true");
        }
        NETWORK = NetworkRegistry.ChannelBuilder.
                named(new ResourceLocation(MODID, MAIN_CHANNEL)).
                clientAcceptedVersions(PROTOCOL_VERSION::equals).
                serverAcceptedVersions(PROTOCOL_VERSION::equals).
                networkProtocolVersion(() -> PROTOCOL_VERSION).
                simpleChannel();
        NETWORK.registerMessage(currMessageId++, PacketRelocator.class, PacketRelocator::encode, PacketRelocator::decode, PacketRelocator::handle);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event){
        AdvRecipeRegistry.INSTANCE.registerListener(GravisuitRecipes::loadRecipes);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new GraviSuitOverlay(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onEntityRenderersAdd(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Registry.PLASMA_BALL_ENTITY_TYPE, PlasmaBallRenderer::new);
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
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ENTITY_TYPES)){
            event.register(ForgeRegistries.Keys.ENTITY_TYPES, new ResourceLocation(MODID, "plasma_ball"), () -> Registry.PLASMA_BALL_ENTITY_TYPE);
        }
    }
}
