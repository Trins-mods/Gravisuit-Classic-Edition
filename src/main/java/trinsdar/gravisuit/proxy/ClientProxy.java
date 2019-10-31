package trinsdar.gravisuit.proxy;

import ic2.core.platform.textures.Ic2Icons;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import trinsdar.gravisuit.util.Icons;
import trinsdar.gravisuit.util.render.RenderGUIHandler;

public class ClientProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onIconLoad(Ic2Icons.SpriteReloadEvent event) {
        Icons.loadSprites();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new RenderGUIHandler());
    }
}
