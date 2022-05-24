package trinsdar.gravisuit.items;

import ic2.core.IC2;
import ic2.core.utils.IC2ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

import java.util.Arrays;
import java.util.List;

public class ItemComponents extends Item{
    private int index;

    public ItemComponents(String name) {
        super(new Item.Settings().group(IC2ItemGroup.MISC));
        Registry.REGISTRY.put(new Identifier(GravisuitClassic.MODID, name), this);
    }
}

