package trinsdar.gravisuit.util;

import ic2.core.IC2;
import ic2.core.util.obj.plugins.IBaublesPlugin;
import trinsdar.gravisuit.items.ItemComponents;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedElectricJetpack;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedLappack;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedNanoChestplate;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedNuclearJetpack;
import trinsdar.gravisuit.items.armor.ItemArmorAdvancedNuclearNanoChestplate;
import trinsdar.gravisuit.items.armor.ItemArmorGravisuit;
import trinsdar.gravisuit.items.armor.ItemArmorNuclearGravisuit;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemToolAdvancedChainsaw;
import trinsdar.gravisuit.items.tools.ItemToolAdvancedDiamondDrill;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.items.tools.ItemToolVajra;
import trinsdar.gravisuit.util.baubles.ItemArmorBaublesAdvancedElectricJetpack;
import trinsdar.gravisuit.util.baubles.ItemArmorBaublesAdvancedLappack;
import trinsdar.gravisuit.util.baubles.ItemArmorBaublesAdvancedNuclearJetpack;

public class Registry {
    private static ItemArmorAdvancedElectricJetpack advancedElectricJetpack;
    private static ItemArmorAdvancedNuclearJetpack advancedNuclearJetpack;
    public static final ItemArmorAdvancedNanoChestplate advancedNanoChestplate = new ItemArmorAdvancedNanoChestplate(new ItemArmorAdvancedElectricJetpack(), "advancedNanoChestplate", GravisuitLang.advancedNanoChestplate, "advanced_nano_chestplate", 15);
    public static final ItemArmorAdvancedNuclearNanoChestplate advancedNuclearNanoChestplate = new ItemArmorAdvancedNuclearNanoChestplate();
    public static final ItemArmorGravisuit gravisuit = new ItemArmorGravisuit();
    public static final ItemArmorNuclearGravisuit nuclearGravisuit = new ItemArmorNuclearGravisuit();
    private static ItemArmorAdvancedLappack advancedLappack;
    private static ItemArmorAdvancedLappack ultimateLappack;
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

    public static ItemArmorAdvancedElectricJetpack getAdvancedElectricJetpack() {
        return advancedElectricJetpack;
    }

    public static ItemArmorAdvancedNuclearJetpack getAdvancedNuclearJetpack() {
        return advancedNuclearJetpack;
    }

    public static ItemArmorAdvancedLappack getAdvancedLappack() {
        return advancedLappack;
    }

    public static ItemArmorAdvancedLappack getUltimateLappack() {
        return ultimateLappack;
    }

    public static void initVars(){
        IBaublesPlugin plugin = IC2.loader.getPlugin("baubles", IBaublesPlugin.class);
        if (plugin != null){
            advancedElectricJetpack = new ItemArmorBaublesAdvancedElectricJetpack();
            advancedNuclearJetpack = new ItemArmorBaublesAdvancedNuclearJetpack();
            advancedLappack = new ItemArmorBaublesAdvancedLappack("advancedLappack", GravisuitLang.advancedLappack, 2, GravisuitConfig.powerValues.advancedLappackStorage, 16, GravisuitConfig.powerValues.advancedLappackTransfer);
            ultimateLappack = new ItemArmorBaublesAdvancedLappack("ultimateLappack", GravisuitLang.ultimateLappack, 3, GravisuitConfig.powerValues.ultimateLappackStorage, 19, GravisuitConfig.powerValues.ultimateLappackTransfer);
        }else {
            advancedElectricJetpack = new ItemArmorAdvancedElectricJetpack();
            advancedNuclearJetpack = new ItemArmorAdvancedNuclearJetpack();
            advancedLappack = new ItemArmorAdvancedLappack("advancedLappack", GravisuitLang.advancedLappack, 2, GravisuitConfig.powerValues.advancedLappackStorage, 16, GravisuitConfig.powerValues.advancedLappackTransfer);
            ultimateLappack = new ItemArmorAdvancedLappack("ultimateLappack", GravisuitLang.ultimateLappack, 3, GravisuitConfig.powerValues.ultimateLappackStorage, 19, GravisuitConfig.powerValues.ultimateLappackTransfer);
        }
    }

    public static void init(){
        if (GravisuitConfig.enabledItems.enableAdvancedElectricJetpack){
            IC2.getInstance().createItem(advancedElectricJetpack);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedNuclearJetpack){
            IC2.getInstance().createItem(advancedNuclearJetpack);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedNanoChestplate){
            IC2.getInstance().createItem(advancedNanoChestplate);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedNuclearNanoChestplate){
            IC2.getInstance().createItem(advancedNuclearNanoChestplate);
        }
        if (GravisuitConfig.enabledItems.enableGravisuit){
            IC2.getInstance().createItem(gravisuit);
        }
        if (GravisuitConfig.enabledItems.enableNuclearGravisuit){
            IC2.getInstance().createItem(nuclearGravisuit);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedLappack){
            IC2.getInstance().createItem(advancedLappack);
        }
        if (GravisuitConfig.enabledItems.enableUltimateLappack){
            IC2.getInstance().createItem(ultimateLappack);
        }
        if (GravisuitConfig.enabledItems.enableGravitool){
            IC2.getInstance().createItem(gravitool);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedDrill){
            IC2.getInstance().createItem(advancedDiamondDrill);
        }
        if (GravisuitConfig.enabledItems.enableAdvancedChainsaw){
            IC2.getInstance().createItem(advancedChainsaw);
        }
        if (GravisuitConfig.enabledItems.enableVajra){
            IC2.getInstance().createItem(vajra);
        }
        if (GravisuitConfig.enabledItems.enableMiscCraftingItems){
            IC2.getInstance().createItem(superConductorCover);
            IC2.getInstance().createItem(superConductor);
            IC2.getInstance().createItem(coolingCore);
            IC2.getInstance().createItem(gravitationEngine);
            IC2.getInstance().createItem(magnetron);
            IC2.getInstance().createItem(vajraCore);
            IC2.getInstance().createItem(engineBoost);
        }
        IC2.getInstance().createItem(relocator);
    }
}
