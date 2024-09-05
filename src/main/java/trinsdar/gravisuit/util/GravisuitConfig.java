package trinsdar.gravisuit.util;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.toml.TomlParser;
import ic2.core.IC2;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;

public class GravisuitConfig {
    public static ConfigEntry.EnumValue<Positions> POSITIONS;
    public static ConfigEntry.EnumValue<HudMode> HUD_MODE;

    public static ConfigEntry.IntValue ADVANCED_ELECTRIC_JETPACK_STORAGE, ADVANCED_NUCLEAR_JETPACK_STORAGE, ADVANCED_LAPPACK_STORAGE, ULTIMATE_LAPPACK_STORAGE, GRAVITATION_JETPACK_STORAGE,
            NUCLEAR_GRAVITATION_JETPACK_STORAGE, GRAVITOOL_STORAGE, VAJRA_STORAGE, RELOCATOR_STORAGE, MAGNET_STORAGE;
    public static ConfigEntry.IntValue ADVANCED_ELECTRIC_JETPACK_TRANSFER, ADVANCED_LAPPACK_TRANSFER, ULTIMATE_LAPPACK_TRANSFER, GRAVITATION_JETPACK_TRANSFER,
            GRAVITOOL_TRANSFER, VAJRA_TRANSFER, RELOCATOR_TRANSFER, MAGNET_TRANSFER;

    public static ConfigEntry.BoolValue ADVANCED_JETPACK_PROVIDE_ENERGY, ADVANCED_N_JETPACK_PROVIDE_ENERGY, COMPACTED_JETPACK_PROVIDE_ENERGY, COMPACTED_N_JETPACK_PROVIDE_ENERGY,
            GRAVITATION_JETPACK_PROVIDE_ENERGY, GRAVITATION_N_JETPACK_PROVIDE_ENERGY;
    static ConfigHandler CONFIG;

    public static void createConfig(){
        Config config = new Config("ic2c/gravisuit");
        ConfigSection client = config.add("client");
        POSITIONS = client.addEnum("positions", Positions.TOPLEFT, Positions.class, "Position of the gravisuit Hud");
        HUD_MODE = client.addEnum("hud_mode", HudMode.ON, HudMode.class, "Hud mode for the gravisuit Hud");
        ConfigSection powerValues = config.add("power_values");
        ConfigSection storage = powerValues.addSubSection("storage");
        ADVANCED_ELECTRIC_JETPACK_STORAGE = storage.addInt("advanced_electric_jetpack_storage", 200000).setMin(1);
        ADVANCED_NUCLEAR_JETPACK_STORAGE = storage.addInt("advanced_nuclear_jetpack_storage", 200000).setMin(1);
        ADVANCED_LAPPACK_STORAGE = storage.addInt("advanced_lappack_storage", 600000).setMin(1);
        ULTIMATE_LAPPACK_STORAGE = storage.addInt("ultimate_lappack_storage", 10000000).setMin(1);
        GRAVITATION_JETPACK_STORAGE = storage.addInt("gravitation_jetpack_storage", 500000).setMin(1);
        NUCLEAR_GRAVITATION_JETPACK_STORAGE = storage.addInt("nuclear_gravitation_jetpack_storage", 500000).setMin(1);
        GRAVITOOL_STORAGE = storage.addInt("gravitool_storage", 50000).setMin(1);
        VAJRA_STORAGE = storage.addInt("vajra_storage", 3000000).setMin(1);
        RELOCATOR_STORAGE = storage.addInt("relocator_storage", 50000000).setMin(1);
        MAGNET_STORAGE = storage.addInt("magnet_storage", 10000).setMin(1);
        ConfigSection transfer = powerValues.addSubSection("transfer");
        ADVANCED_ELECTRIC_JETPACK_TRANSFER = transfer.addInt("advanced_electric_jetpack_transfer", 500).setMin(1);
        ADVANCED_LAPPACK_TRANSFER = transfer.addInt("advanced_lappack_transfer", 500).setMin(1);
        ULTIMATE_LAPPACK_TRANSFER = transfer.addInt("ultimate_lappack_transfer", 4000).setMin(1);
        GRAVITATION_JETPACK_TRANSFER = transfer.addInt("gravitation_jetpack_transfer", 1000).setMin(1);
        GRAVITOOL_TRANSFER = transfer.addInt("gravitool_transfer", 400).setMin(1);
        VAJRA_TRANSFER = transfer.addInt("vajra_transfer", 1000).setMin(1);
        RELOCATOR_TRANSFER = transfer.addInt("relocator_transfer", 25000).setMin(1);
        MAGNET_TRANSFER = transfer.addInt("magnet_transfer", 500).setMin(1);
        ConfigSection misc = config.add("misc");
        ADVANCED_JETPACK_PROVIDE_ENERGY = misc.addBool("advanced_jetpack_provide_energy", false, "Enables the Advanced Electric jetpack charging items. Default: false");
        ADVANCED_N_JETPACK_PROVIDE_ENERGY = misc.addBool("advanced_n_jetpack_provide_energy", true, "Enables the Advanced Nuclear jetpack charging items. Default: true");
        COMPACTED_JETPACK_PROVIDE_ENERGY = misc.addBool("compacted_jetpack_provide_energy", false, "Enables the Compacted jetpack charging items. Default: false");
        COMPACTED_N_JETPACK_PROVIDE_ENERGY = misc.addBool("compacted_n_jetpack_provide_energy", true, "Enables the Compacted Nuclear jetpack charging items. Default: true");
        GRAVITATION_JETPACK_PROVIDE_ENERGY = misc.addBool("gravitation_jetpack_provide_energy", false, "Enables the Gravitation jetpack charging items. Default: false");
        GRAVITATION_N_JETPACK_PROVIDE_ENERGY = misc.addBool("gravitation_n_jetpack_provide_energy", true, "Enables the Gravitation Nuclear jetpack charging items. Default: true");
        CONFIG = CarbonConfig.CONFIGS.createConfig(config);
        CONFIG.register();
        Path configFile = Path.of(FMLPaths.CONFIGDIR.get().toString(),  "gravisuit-client.toml");
        if (Files.exists(configFile)){
            try {
                CommentedConfig forgeConfig = PARSER.parse(configFile, FileNotFoundAction.READ_NOTHING);
                if (forgeConfig.get("POSITION") instanceof String string) {
                    try {
                        Positions centerIngot = Positions.valueOf(string);
                        POSITIONS.set(centerIngot);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
                Files.move(configFile, Path.of(FMLPaths.CONFIGDIR.get().toString(),  "gravisuit-client-replaced.toml.bak"));
                CONFIG.save();
            } catch (Exception e){
                IC2.LOGGER.error(e);
            }
        }
        configFile = Path.of(FMLPaths.CONFIGDIR.get().toString(),  "gravisuit-common.toml");
        if (Files.exists(configFile)) {
            CommentedConfig forgeConfig = PARSER.parse(configFile, FileNotFoundAction.READ_NOTHING);
            try {
                String path = "PowerValues.Storage";
                ADVANCED_ELECTRIC_JETPACK_STORAGE.set(getInt(forgeConfig, path + ".ADVANCED_ELECTRIC_JETPACK_STORAGE"));
                ADVANCED_NUCLEAR_JETPACK_STORAGE.set(getInt(forgeConfig, path + ".ADVANCED_NUCLEAR_JETPACK_STORAGE"));
                ADVANCED_LAPPACK_STORAGE.set(getInt(forgeConfig, path + ".ADVANCED_LAPPACK_STORAGE"));
                ULTIMATE_LAPPACK_STORAGE.set(getInt(forgeConfig, path + ".ULTIMATE_LAPPACK_STORAGE"));
                GRAVITATION_JETPACK_STORAGE.set(getInt(forgeConfig, path + ".GRAVITATION_JETPACK_STORAGE"));
                NUCLEAR_GRAVITATION_JETPACK_STORAGE.set(getInt(forgeConfig, path + ".NUCLEAR_GRAVITATION_JETPACK_STORAGE"));
                GRAVITOOL_STORAGE.set(getInt(forgeConfig, path + ".GRAVITOOL_STORAGE"));
                VAJRA_STORAGE.set(getInt(forgeConfig, path + ".VAJRA_STORAGE"));
                RELOCATOR_STORAGE.set(getInt(forgeConfig, path + ".RELOCATOR_STORAGE"));
                path = "PowerValues.Transfer";
                ADVANCED_ELECTRIC_JETPACK_TRANSFER.set(getInt(forgeConfig, path + ".ADVANCED_ELECTRIC_JETPACK_TRANSFER"));
                ADVANCED_LAPPACK_TRANSFER.set(getInt(forgeConfig, path + ".ADVANCED_LAPPACK_TRANSFER"));
                ULTIMATE_LAPPACK_TRANSFER.set(getInt(forgeConfig, path + ".ULTIMATE_LAPPACK_TRANSFER"));
                GRAVITATION_JETPACK_TRANSFER.set(getInt(forgeConfig, path + ".GRAVITATION_JETPACK_TRANSFER"));
                GRAVITOOL_TRANSFER.set(getInt(forgeConfig, path + ".GRAVITOOL_TRANSFER"));
                VAJRA_TRANSFER.set(getInt(forgeConfig, path + ".VAJRA_TRANSFER"));
                RELOCATOR_TRANSFER.set(getInt(forgeConfig, path + ".RELOCATOR_TRANSFER"));
                path = "Misc";
                ADVANCED_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".ADVANCED_JETPACK_PROVIDE_ENERGY"));
                ADVANCED_N_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".ADVANCED_N_JETPACK_PROVIDE_ENERGY"));
                COMPACTED_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".COMPACTEd_JETPACK_PROVIDE_ENERGY"));
                COMPACTED_N_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".COMPACTEd_N_JETPACK_PROVIDE_ENERGY"));
                GRAVITATION_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".GRAVITATION_JETPACK_PROVIDE_ENERGY"));
                GRAVITATION_N_JETPACK_PROVIDE_ENERGY.set(getBoolean(forgeConfig, path + ".GRAVITATION_N_JETPACK_PROVIDE_ENERGY"));
                Files.move(configFile, Path.of(FMLPaths.CONFIGDIR.get().toString(),  "gravisuit-common-replaced.toml.bak"));
                CONFIG.save();
            } catch (Exception e) {
                IC2.LOGGER.error(e);
            }
        }
    }


    private static final TomlParser PARSER = new TomlParser();

    private static int getInt(CommentedConfig config, String path){
        if (!config.contains(path)){
            throw new RuntimeException("Path does not exist in old config! " + path);
        }
        return config.getInt(path);
    }

    private static boolean getBoolean(CommentedConfig config, String path){
        if (!config.contains(path)){
            throw new RuntimeException("Path does not exist in old config! " + path);
        }
        return config.getRaw(path);
    }

    public enum Positions {
        TOPLEFT, TOPRIGHT, TOPMIDDLE, BOTTOMLEFT, BOTTOMRIGHT, BOTTOMLEFT_HOTBAR, BOTTOMRIGHT_HOTBAR
    }

    public enum HudMode {
        OFF, REQUIRES_HUD_UPGRADE, ON
    }
}
