package trinsdar.gravisuit.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import trinsdar.gravisuit.items.ItemArmorAdvancedElectricJetpack;
import trinsdar.gravisuit.items.ItemArmorAdvancedLappack;
import trinsdar.gravisuit.items.ItemArmorAdvancedNanoChestplate;
import trinsdar.gravisuit.items.ItemArmorGravisuit;

public class Registry {
    public static final ItemArmorAdvancedElectricJetpack advancedElectricJetpack = new ItemArmorAdvancedElectricJetpack();
    public static final ItemArmorAdvancedNanoChestplate advancedNanoChestplate = new ItemArmorAdvancedNanoChestplate();
    public static final ItemArmorGravisuit gravisuit = new ItemArmorGravisuit();
    public static final ItemArmorAdvancedLappack advancedLappack = new ItemArmorAdvancedLappack();

    public static final Item[] items = {
            advancedElectricJetpack,
            advancedNanoChestplate,
            gravisuit,
            advancedLappack
    };
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry registry = event.getRegistry();

        for (Item item : items)
        {
            registry.register(item);
        }
    }
}
