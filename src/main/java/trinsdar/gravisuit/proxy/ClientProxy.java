package trinsdar.gravisuit.proxy;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;

import java.sql.Ref;

public class ClientProxy{
    public static void registerBatteryPropertyOverrides(ItemToolGravitool gravitool) {
        ItemProperties.register(gravitool, new ResourceLocation(GravisuitClassic.MODID, "gravitool"), (stack, world, living, i) -> {
            byte mode = gravitool.getMode(stack);
            return mode;
        });
    }
}
