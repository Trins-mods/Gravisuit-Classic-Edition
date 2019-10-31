package trinsdar.gravisuit.util.render;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderGUIHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
    	if (event.getType() != ElementType.EXPERIENCE) return;
    	new GUIHandler(Minecraft.getMinecraft());
    }

}
