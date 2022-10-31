package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

import java.util.function.Supplier;

public class ItemAdvancedLappack extends ElectricPackArmor {
    final Supplier<Integer> capacity;
    final Supplier<Integer> transferLimit;
    public ItemAdvancedLappack(String itemName, Rarity rarity, Supplier<Integer> capacity, int tier, Supplier<Integer> transferLimit) {
        super(itemName, "lappack", itemName, "gravisuit:textures/models/" + itemName, rarity, 0, tier, 0);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, itemName), this);
        this.capacity = capacity;
        this.transferLimit = transferLimit;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return capacity.get();
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return transferLimit.get();
    }
}
