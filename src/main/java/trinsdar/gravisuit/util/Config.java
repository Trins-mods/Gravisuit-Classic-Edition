package trinsdar.gravisuit.util;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.proxy.CommonProxy;

public class Config {
    private static final String CATEGORY_POWER_VALUES = "power values";
    private static final String CATEGORY_ENABLED_ITEMS = "enabled items";
    private static final String CATEGORY_MISC = "misc";

    public static int
    advancedElectricJetpackStorage = 500000,
    advancedNuclearJetpackStorage = 500000,
    advancedLappackStorage = 600000,
    ultimateLappackStorage = 10000000,
    advancedNanoChestplateStorage = 600000,
    advancedNuclearNanoChestplateStorage = 600000,
    gravisuitStorage = 10000000,
    nuclearGravisuitStorage = 10000000,
    advancedChainsawStorage = 100000,
    advancedDrillStorage = 100000,
    gravitoolStorage = 50000,
    vajraStorage = 3000000;

    public static int
    advancedElectricJetpackTransfer = 500,
    advancedNuclearJetpackTransfer = 500,
    advancedLappackTransfer = 500,
    ultimateLappackTransfer = 4000,
    advancedNanoChestplateTransfer = 500,
    advancedNuclearNanoChestplateTransfer = 500,
    gravisuitTransfer = 5000,
    nuclearGravisuitTransfer = 5000,
    advancedChainsawTransfer = 200,
    advancedDrillTransfer = 200,
    gravitoolTransfer = 400,
    vajraTransfer = 1000;

    public static boolean
    enableAdvancedElectricJetpack = true,
    enableAdvancedNuclearJetpack = true,
    enableAdvancedLappack = true,
    enableUltimateLappack = true,
    enableAdvancedNanoChestplate = true,
    enableAdvancedNuclearNanoChestplate = true,
    enableGravisuit = true,
    enableNuclearGravisuit = true,
    enableAdvancedChainsaw = true,
    enableAdvancedDrill = true,
    enableGravitool = true,
    enableVajra = true,
    enableMiscCraftingItems = true;

    public static boolean
    enableAdvancedDrill3x3Mode = true,
    enableAdvancedDrill2x3Mode = true,
    enableAdvancedDrill1x2Mode = true,
    enableCompactedElectricJetpackOverride = true,
    enableCompactedNuclearJetpackOverride = true,
    enableQuantumJetplateOverride = true,
    enableQuantumNuclearJetplateOverride = true,
    enableIc2JetpackRecipOverrides = true,
    enableGravitoolRequiresLosslessPrecisionWrench = true;

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            GravisuitClassic.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_POWER_VALUES, "Set the max EU storage and max EU transfer of each item here.");
        advancedElectricJetpackStorage = cfg.getInt("advancedElectricJetpackStorage", CATEGORY_POWER_VALUES, advancedElectricJetpackStorage, 1, Integer.MAX_VALUE, "");
        advancedNuclearJetpackStorage = cfg.getInt("advancedNuclearJetpackStorage", CATEGORY_POWER_VALUES, advancedNuclearJetpackStorage, 1, Integer.MAX_VALUE, "");
        advancedLappackStorage = cfg.getInt("advancedLappackStorage", CATEGORY_POWER_VALUES, advancedLappackStorage, 1, Integer.MAX_VALUE, "");
        ultimateLappackStorage = cfg.getInt("ultimateLappackStorage", CATEGORY_POWER_VALUES, ultimateLappackStorage, 1, Integer.MAX_VALUE, "");
        advancedNanoChestplateStorage = cfg.getInt("advancedNanoChestplateStorage", CATEGORY_POWER_VALUES, advancedNanoChestplateStorage, 1, Integer.MAX_VALUE, "");
        advancedNuclearNanoChestplateStorage = cfg.getInt("advancedNuclearNanoChestplateStorage", CATEGORY_POWER_VALUES, advancedNuclearNanoChestplateStorage, 1, Integer.MAX_VALUE, "");
        gravisuitStorage = cfg.getInt("gravisuitStorage", CATEGORY_POWER_VALUES, gravisuitStorage, 1, Integer.MAX_VALUE, "");
        nuclearGravisuitStorage = cfg.getInt("nuclearGravisuitStorage", CATEGORY_POWER_VALUES, nuclearGravisuitStorage, 1, Integer.MAX_VALUE, "");
        advancedChainsawStorage = cfg.getInt("advancedChainsawStorage", CATEGORY_POWER_VALUES, advancedChainsawStorage, 1, Integer.MAX_VALUE, "");
        advancedDrillStorage = cfg.getInt("advancedDrillStorage", CATEGORY_POWER_VALUES, advancedDrillStorage, 1, Integer.MAX_VALUE, "");
        gravitoolStorage = cfg.getInt("gravitoolStorage", CATEGORY_POWER_VALUES, gravitoolStorage, 1, Integer.MAX_VALUE, "");
        vajraStorage = cfg.getInt("vajraStorage", CATEGORY_POWER_VALUES, vajraStorage, 1, Integer.MAX_VALUE, "");

        advancedElectricJetpackTransfer = cfg.getInt("advancedElectricJetpackTransfer", CATEGORY_POWER_VALUES, advancedElectricJetpackTransfer, 1, Integer.MAX_VALUE, "");
        advancedNuclearJetpackTransfer = cfg.getInt("advancedNuclearJetpackTransfer", CATEGORY_POWER_VALUES, advancedNuclearJetpackTransfer, 1, Integer.MAX_VALUE, "");
        advancedLappackTransfer = cfg.getInt("advancedLappackTransfer", CATEGORY_POWER_VALUES, advancedLappackTransfer, 1, Integer.MAX_VALUE, "");
        ultimateLappackTransfer = cfg.getInt("ultimateLappackTransfer", CATEGORY_POWER_VALUES, ultimateLappackTransfer, 1, Integer.MAX_VALUE, "");
        advancedNanoChestplateTransfer = cfg.getInt("advancedNanoChestplateTransfer", CATEGORY_POWER_VALUES, advancedNanoChestplateTransfer, 1, Integer.MAX_VALUE, "");
        advancedNuclearNanoChestplateTransfer = cfg.getInt("advancedNuclearNanoChestplateTransfer", CATEGORY_POWER_VALUES, advancedNuclearNanoChestplateTransfer, 1, Integer.MAX_VALUE, "");
        gravisuitTransfer = cfg.getInt("gravisuitTransfer", CATEGORY_POWER_VALUES, gravisuitTransfer, 1, Integer.MAX_VALUE, "");
        nuclearGravisuitTransfer = cfg.getInt("nuclearGravisuitTransfer", CATEGORY_POWER_VALUES, nuclearGravisuitTransfer, 1, Integer.MAX_VALUE, "");
        advancedChainsawTransfer = cfg.getInt("advancedChainsawTransfer", CATEGORY_POWER_VALUES, advancedChainsawTransfer, 1, Integer.MAX_VALUE, "");
        advancedDrillTransfer = cfg.getInt("advancedDrillTransfer", CATEGORY_POWER_VALUES, advancedDrillTransfer, 1, Integer.MAX_VALUE, "");
        gravitoolTransfer = cfg.getInt("gravitoolTransfer", CATEGORY_POWER_VALUES, gravitoolTransfer, 1, Integer.MAX_VALUE, "");
        vajraTransfer = cfg.getInt("vajraTransfer", CATEGORY_POWER_VALUES, vajraTransfer, 1, Integer.MAX_VALUE, "");

        cfg.addCustomCategoryComment(CATEGORY_ENABLED_ITEMS, "Enable or Disable each item here.");

        enableAdvancedElectricJetpack = cfg.getBoolean("enableAdvancedElectricJetpack", CATEGORY_ENABLED_ITEMS, enableAdvancedElectricJetpack, "");
        enableAdvancedNuclearJetpack = cfg.getBoolean("enableAdvancedNuclearJetpack", CATEGORY_ENABLED_ITEMS, enableAdvancedNuclearJetpack, "");
        enableAdvancedLappack = cfg.getBoolean("enableAdvancedLappack", CATEGORY_ENABLED_ITEMS, enableAdvancedLappack, "");
        enableUltimateLappack = cfg.getBoolean("enableUltimateLappack", CATEGORY_ENABLED_ITEMS, enableUltimateLappack, "");
        enableAdvancedNanoChestplate = cfg.getBoolean("enableAdvancedNanoChestplate", CATEGORY_ENABLED_ITEMS, enableAdvancedNanoChestplate, "");
        enableAdvancedNuclearNanoChestplate = cfg.getBoolean("enableAdvancedNuclearNanoChestplate", CATEGORY_ENABLED_ITEMS, enableAdvancedNuclearNanoChestplate, "");
        enableGravisuit = cfg.getBoolean("enableGravisuit", CATEGORY_ENABLED_ITEMS, enableGravisuit, "");
        enableNuclearGravisuit = cfg.getBoolean("enableNuclearGravisuit", CATEGORY_ENABLED_ITEMS, enableNuclearGravisuit, "");
        enableAdvancedChainsaw = cfg.getBoolean("enableAdvancedChainsaw", CATEGORY_ENABLED_ITEMS, enableAdvancedChainsaw, "");
        enableAdvancedDrill = cfg.getBoolean("enableAdvancedDrill", CATEGORY_ENABLED_ITEMS, enableAdvancedDrill, "");
        enableGravitool = cfg.getBoolean("enableGravitool", CATEGORY_ENABLED_ITEMS, enableGravitool, "");
        enableVajra = cfg.getBoolean("enableVajra", CATEGORY_ENABLED_ITEMS, enableVajra, "");
        enableMiscCraftingItems = cfg.getBoolean("enableMiscCraftingItems", CATEGORY_ENABLED_ITEMS, enableMiscCraftingItems, "");

        enableAdvancedDrill3x3Mode = cfg.getBoolean("enableAdvancedDrill3x3Mode", CATEGORY_MISC, enableAdvancedDrill3x3Mode, "Enable or Disable the 3x3 mining mode of the advanced drill here.");
        enableAdvancedDrill2x3Mode = cfg.getBoolean("enableAdvancedDrill2x3Mode", CATEGORY_MISC, enableAdvancedDrill2x3Mode, "Enable or Disable the 2x3 mining mode of the advanced drill here.");
        enableAdvancedDrill1x2Mode = cfg.getBoolean("enableAdvancedDrill1x2Mode", CATEGORY_MISC, enableAdvancedDrill1x2Mode, "Enable or Disable the 1x2 mining mode of the advanced drill here.");
        enableCompactedElectricJetpackOverride = cfg.getBoolean("enableCompactedElectricJetpackOverride", CATEGORY_MISC, enableCompactedElectricJetpackOverride, "Enable or Disable the compacted electric jetpack charging items like a lappack does here.");
        enableCompactedNuclearJetpackOverride = cfg.getBoolean("enableCompactedNuclearJetpackOverride", CATEGORY_MISC, enableCompactedNuclearJetpackOverride, "Enable or Disable the compacted nuclear jetpack charging items like a lappack does here.");
        enableQuantumJetplateOverride = cfg.getBoolean("enableQuantumJetplateOverride", CATEGORY_MISC, enableQuantumJetplateOverride, "Enable or Disable the quantum jetplate charging items like a lappack does here.");
        enableQuantumNuclearJetplateOverride = cfg.getBoolean("enableQuantumNuclearJetplateOverride", CATEGORY_MISC, enableQuantumNuclearJetplateOverride, "Enable or Disable the quantum nuclear jetplate charging items like a lappack does here.");
        enableIc2JetpackRecipOverrides = cfg.getBoolean("enableIc2JetpackRecipOverrides", CATEGORY_MISC, enableIc2JetpackRecipOverrides, "Enable or Disable the overriding of compacted jetpack and jetplate recipes here. Also requires that the configs for making them charge items also be enabled.");
        enableGravitoolRequiresLosslessPrecisionWrench = cfg.getBoolean("enableGravitoolRequiresLosslessPrecisionWrench", CATEGORY_MISC, enableGravitoolRequiresLosslessPrecisionWrench, "Enable or Disable the gravitool requiring the completly lossless version of the precision wrench here.");
    }
}

