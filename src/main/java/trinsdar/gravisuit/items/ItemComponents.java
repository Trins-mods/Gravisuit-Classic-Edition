package trinsdar.gravisuit.items;

import ic2.core.utils.IC2ItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

public class ItemComponents extends Item {
    private int index;

    public ItemComponents(String name) {
        super(new Item.Properties().tab(IC2ItemGroup.TAB_MISC));
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, name), this);
    }
}

