package trinsdar.gravisuit;

import ic2.core.IC2;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.items.tools.ItemToolVajra;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.proxy.CommonProxy;
import trinsdar.gravisuit.util.*;
import trinsdar.gravisuit.util.render.GraviSuitOverlay;
import trinsdar.gravisuit.util.render.PlasmaBallRenderer;

import static trinsdar.gravisuit.util.Registry.REGISTRY;

@Mod(GravisuitClassic.MODID)
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String networkChannelName = MODID;

    private static final String MAIN_CHANNEL = "main_channel";
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public static SimpleChannel NETWORK;

    public static CommonProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    private int currMessageId = 0;

    public GravisuitClassic(){
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        IC2.EVENT_BUS.register(GravisuitWiki.class);
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
        GravisuitConfig.createConfig();
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event){
        AdvRecipeRegistry.INSTANCE.registerListener(GravisuitRecipes::loadRecipes);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(Registry.PLASMA_PORTAL, RenderType.cutoutMipped());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("gravisuit_overlay", new GraviSuitOverlay(Minecraft.getInstance()));
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
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)){
            Registry.init();
            event.register(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(MODID, "plasma_portal"), () -> Registry.PLASMA_PORTAL);
        }
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)){
            REGISTRY.forEach((r, i) -> event.register(ForgeRegistries.Keys.ITEMS, r, () -> i));
        }
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ENTITY_TYPES)){
            event.register(ForgeRegistries.Keys.ENTITY_TYPES, new ResourceLocation(MODID, "plasma_ball"), () -> Registry.PLASMA_BALL_ENTITY_TYPE);
        }
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES)){
            event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, new ResourceLocation(MODID, "plasma_portal"), () -> Registry.PLASMA_PORTAL_BLOCK_ENTITY);
        }
    }
}
