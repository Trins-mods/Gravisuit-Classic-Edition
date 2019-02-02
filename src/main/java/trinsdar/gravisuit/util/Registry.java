package trinsdar.gravisuit.util;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import trinsdar.gravisuit.items.ItemComponents;
import trinsdar.gravisuit.items.ItemComponents.ItemComponentTypes;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedElectricJetpack;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedLappack;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedNanoChestplate;
import trinsdar.gravisuit.items.armor.ItemArmorGravisuit;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemToolAdvancedChainsaw;
import trinsdar.gravisuit.items.tools.ItemToolAdvancedDiamondDrill;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.items.tools.ItemToolVajra;

public class Registry {
    public static final ItemArmorAdvancedElectricJetpack advancedElectricJetpack = new ItemArmorAdvancedElectricJetpack();
    public static final ItemArmorAdvancedNanoChestplate advancedNanoChestplate = new ItemArmorAdvancedNanoChestplate();
    public static final ItemArmorGravisuit gravisuit = new ItemArmorGravisuit();
    public static final ItemArmorAdvancedLappack advancedLappack = new ItemArmorAdvancedLappack("advanced_lappack", "advancedLappack", 2, 600000, 16, 500);
    public static final ItemArmorAdvancedLappack ultimateLappack = new ItemArmorAdvancedLappack("ultimate_lappack", "ultimateLappack", 4, 10000000, 19, 2000);
    public static final ItemToolGravitool gravitool = new ItemToolGravitool();
    public static final ItemToolAdvancedDiamondDrill advancedDiamondDrill = new ItemToolAdvancedDiamondDrill();
    public static final ItemToolAdvancedChainsaw advancedChainsaw = new ItemToolAdvancedChainsaw();
    public static final ItemToolVajra vajra = new ItemToolVajra();
    public static final ItemRelocator relocator = new ItemRelocator();
    public static final ItemComponents
    superConductorCover = new ItemComponents(ItemComponentTypes.SUPER_CONDUCTOR_COVER),
    superConductor = new ItemComponents(ItemComponentTypes.SUPER_CONDUCTOR),
    coolingCore = new ItemComponents(ItemComponentTypes.COOLING_CORE),
    gravitationEngine = new ItemComponents(ItemComponentTypes.GRAVITATION_ENGINE),
    magnetron = new ItemComponents(ItemComponentTypes.MAGNETRON),
    vajraCore = new ItemComponents(ItemComponentTypes.VAJRA_CORE),
    engineBoost = new ItemComponents(ItemComponentTypes.ENGINE_BOOST);

    public static final Item[] items = {
            advancedElectricJetpack,
            advancedNanoChestplate,
            gravisuit,
            advancedLappack,
            ultimateLappack,
            gravitool,
            advancedDiamondDrill,
            advancedChainsaw,
            vajra,
            relocator,

            superConductorCover,
            superConductor,
            coolingCore,
            gravitationEngine,
            magnetron,
            vajraCore,
            engineBoost
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
