package trinsdar.gravisuit.proxy;


import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;

import java.sql.Ref;

public class ClientProxy{
    public static void registerBatteryPropertyOverrides(ItemToolGravitool gravitool) {
        ModelPredicateProviderRegistry.register(gravitool, new Identifier(GravisuitClassic.MODID, "gravitool"), (stack, world, living) -> {
            byte mode = gravitool.getMode(stack);
            return mode;
        });
    }
}
