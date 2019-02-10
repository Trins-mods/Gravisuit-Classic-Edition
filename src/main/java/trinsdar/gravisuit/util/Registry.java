package trinsdar.gravisuit.util;

import ic2.core.IC2;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import trinsdar.gravisuit.items.ItemComponents;
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
    public static final ItemArmorAdvancedLappack advancedLappack = new ItemArmorAdvancedLappack("advancedLappack", 2, 1000000, 16, 10000);
    public static final ItemArmorAdvancedLappack ultimateLappack = new ItemArmorAdvancedLappack("ultimateLappack", 3, 10000000, 19, 100000);
    public static final ItemToolGravitool gravitool = new ItemToolGravitool();
    public static final ItemToolAdvancedDiamondDrill advancedDiamondDrill = new ItemToolAdvancedDiamondDrill();
    public static final ItemToolAdvancedChainsaw advancedChainsaw = new ItemToolAdvancedChainsaw();
    public static final ItemToolVajra vajra = new ItemToolVajra();
    public static final ItemRelocator relocator = new ItemRelocator();
    public static final ItemComponents
    superConductorCover = new ItemComponents("superConductorCover", 0),
    superConductor = new ItemComponents("superConductor", 1),
    coolingCore = new ItemComponents("coolingCore", 2),
    gravitationEngine = new ItemComponents("gravitationEngine", 3),
    magnetron = new ItemComponents("magnetron", 4),
    vajraCore = new ItemComponents("vajraCore", 5),
    engineBoost = new ItemComponents("engineBoost", 6);


    public static void init(){
        IC2.getInstance().createItem(advancedElectricJetpack);
        IC2.getInstance().createItem(advancedNanoChestplate);
        IC2.getInstance().createItem(gravisuit);
        IC2.getInstance().createItem(advancedLappack);
        IC2.getInstance().createItem(ultimateLappack);
        IC2.getInstance().createItem(gravitool);
        IC2.getInstance().createItem(advancedDiamondDrill);
        IC2.getInstance().createItem(advancedChainsaw);
        IC2.getInstance().createItem(vajra);
        IC2.getInstance().createItem(relocator);
        IC2.getInstance().createItem(superConductorCover);
        IC2.getInstance().createItem(superConductor);
        IC2.getInstance().createItem(coolingCore);
        IC2.getInstance().createItem(gravitationEngine);
        IC2.getInstance().createItem(magnetron);
        IC2.getInstance().createItem(vajraCore);
        IC2.getInstance().createItem(engineBoost);
    }
}
