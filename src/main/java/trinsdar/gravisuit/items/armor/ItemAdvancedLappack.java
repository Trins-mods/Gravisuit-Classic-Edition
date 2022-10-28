package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedLappack extends ElectricPackArmor implements IHasOverlay {
    public ItemAdvancedLappack(String itemName, Rarity rarity, int capacity, int tier, int transferLimit) {
        super(itemName, "lappack", itemName, "gravisuit:textures/models/" + itemName, rarity, capacity, tier, transferLimit);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, itemName), this);
    }

    public ItemAdvancedLappack(String itemName, int capacity, int tier, int transferLimit) {
        this(itemName, null, capacity, tier, transferLimit);
    }
}
