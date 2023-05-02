package trinsdar.gravisuit.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import trinsdar.gravisuit.GravisuitClassic;

@Mod.EventBusSubscriber(modid = GravisuitClassic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GravisuitConfig {
    public static final Client CLIENT = new Client();
    public static final EnabledItems ENABLED_ITEMS = new EnabledItems();
    public static final PowerValues POWER_VALUES = new PowerValues();
    public static final Misc MISC = new Misc();

    public static final ClientConfig CLIENT_CONFIG;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final CommonConfig COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {

        final Pair<CommonConfig, ForgeConfigSpec> COMMON_PAIR = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        final Pair<ClientConfig, ForgeConfigSpec> CLIENT_PAIR = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_CONFIG = CLIENT_PAIR.getLeft();
        CLIENT_SPEC = CLIENT_PAIR.getRight();
        COMMON_CONFIG = COMMON_PAIR.getLeft();
        COMMON_SPEC = COMMON_PAIR.getRight();

    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent e) {
        onModConfigEvent(e.getConfig());
    }

    public static void onModConfigEvent(final ModConfig e) {
        if (e.getModId().equals(GravisuitClassic.MODID)){
            if (e.getSpec() == CLIENT_SPEC) bakeClientConfig();
            else if (e.getSpec() == COMMON_SPEC) bakeCommonConfig();
        }
    }

    public static class Misc {
        public boolean ADVANCED_JETPACK_PROVIDE_ENERGY, ADVANCED_N_JETPACK_PROVIDE_ENERGY, COMPACTED_JETPACK_PROVIDE_ENERGY, COMPACTED_N_JETPACK_PROVIDE_ENERGY,
                GRAVITATION_JETPACK_PROVIDE_ENERGY, GRAVITATION_N_JETPACK_PROVIDE_ENERGY;
    }

    public static class PowerValues {
        public int ADVANCED_ELECTRIC_JETPACK_STORAGE, ADVANCED_NUCLEAR_JETPACK_STORAGE, ADVANCED_LAPPACK_STORAGE, ULTIMATE_LAPPACK_STORAGE, GRAVITATION_JETPACK_STORAGE,
        NUCLEAR_GRAVITATION_JETPACK_STORAGE, GRAVITOOL_STORAGE, VAJRA_STORAGE, RELOCATOR_STORAGE;

        public int ADVANCED_ELECTRIC_JETPACK_TRANSFER, ADVANCED_NUCLEAR_JETPACK_TRANSFER, ADVANCED_LAPPACK_TRANSFER, ULTIMATE_LAPPACK_TRANSFER, GRAVITATION_JETPACK_TRANSFER,
        NUCLEAR_GRAVITATION_JETPACK_TRANSFER, GRAVITOOL_TRANSFER, VAJRA_TRANSFER, RELOCATOR_TRANSFER;
    }

    public static class EnabledItems {
        //TODO keep or remove?
        //@RequiresMcRestart
        public boolean enableAdvancedElectricJetpack = true;
        //@RequiresMcRestart
        public boolean enableAdvancedNuclearJetpack = true;
        //@RequiresMcRestart
        public boolean enableAdvancedLappack = true;
        //@RequiresMcRestart
        public boolean enableUltimateLappack = true;
        //@RequiresMcRestart
        public boolean enableGravitationJetpack = true;
        //@RequiresMcRestart
        public boolean enableNuclearGravitationJetpack = true;
        //@RequiresMcRestart
        public boolean enableGravitool = true;
        //@RequiresMcRestart
        public boolean enableVajra = true;
        //@RequiresMcRestart
        public boolean enableMiscCraftingItems = true;
    }

    public static class Client {
        //@Comment("Set the location of the hud position here.")
        public Client.Positions POSITIONS;

        public enum Positions {
            TOPLEFT, TOPRIGHT, TOPMIDDLE, BOTTOMLEFT, BOTTOMRIGHT
        }
    }

    public static class ClientConfig {

        public final ForgeConfigSpec.EnumValue<Client.Positions> POSITONS;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            POSITONS = builder.comment("Position of the gravisuit Hud")
                    .translation(GravisuitClassic.MODID + ".config.position")
                    .defineEnum("POSITION", Client.Positions.TOPLEFT);

        }

    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue ADVANCED_ELECTRIC_JETPACK_STORAGE, ADVANCED_NUCLEAR_JETPACK_STORAGE, ADVANCED_LAPPACK_STORAGE, ULTIMATE_LAPPACK_STORAGE, GRAVITATION_JETPACK_STORAGE,
                NUCLEAR_GRAVITATION_JETPACK_STORAGE, GRAVITOOL_STORAGE, VAJRA_STORAGE, RELOCATOR_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_ELECTRIC_JETPACK_TRANSFER, ADVANCED_NUCLEAR_JETPACK_TRANSFER, ADVANCED_LAPPACK_TRANSFER, ULTIMATE_LAPPACK_TRANSFER, GRAVITATION_JETPACK_TRANSFER,
                NUCLEAR_GRAVITATION_JETPACK_TRANSFER, GRAVITOOL_TRANSFER, VAJRA_TRANSFER, RELOCATOR_TRANSFER;

        public final ForgeConfigSpec.BooleanValue ADVANCED_JETPACK_PROVIDE_ENERGY, ADVANCED_N_JETPACK_PROVIDE_ENERGY, COMPACTED_JETPACK_PROVIDE_ENERGY, COMPACTED_N_JETPACK_PROVIDE_ENERGY,
                GRAVITATION_JETPACK_PROVIDE_ENERGY, GRAVITATION_N_JETPACK_PROVIDE_ENERGY;
        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("PowerValues");
            builder.push("Storage");
            ADVANCED_ELECTRIC_JETPACK_STORAGE = builder.defineInRange("ADVANCED_ELECTRIC_JETPACK_STORAGE", 200000, 1, Integer.MAX_VALUE);
            ADVANCED_NUCLEAR_JETPACK_STORAGE = builder.defineInRange("ADVANCED_NUCLEAR_JETPACK_STORAGE", 200000, 1, Integer.MAX_VALUE);
            ADVANCED_LAPPACK_STORAGE = builder.defineInRange("ADVANCED_LAPPACK_STORAGE", 600000, 1, Integer.MAX_VALUE);
            ULTIMATE_LAPPACK_STORAGE = builder.defineInRange("ULTIMATE_LAPPACK_STORAGE", 10000000, 1, Integer.MAX_VALUE);
            GRAVITATION_JETPACK_STORAGE = builder.defineInRange("GRAVITATION_JETPACK_STORAGE", 500000, 1, Integer.MAX_VALUE);
            NUCLEAR_GRAVITATION_JETPACK_STORAGE = builder.defineInRange("NUCLEAR_GRAVITATION_JETPACK_STORAGE", 500000, 1, Integer.MAX_VALUE);
            GRAVITOOL_STORAGE = builder.defineInRange("GRAVITOOL_STORAGE", 50000, 1, Integer.MAX_VALUE);
            VAJRA_STORAGE = builder.defineInRange("VAJRA_STORAGE", 3000000, 1 , Integer.MAX_VALUE);
            RELOCATOR_STORAGE = builder.defineInRange("RELOCATOR_STORAGE", 50000000, 1, Integer.MAX_VALUE);
            builder.pop();
            builder.push("Transfer");
            ADVANCED_ELECTRIC_JETPACK_TRANSFER = builder.defineInRange("ADVANCED_ELECTRIC_JETPACK_TRANSFER", 500, 1, Integer.MAX_VALUE);
            ADVANCED_NUCLEAR_JETPACK_TRANSFER = builder.defineInRange("ADVANCED_NUCLEAR_JETPACK_TRANSFER", 500, 1, Integer.MAX_VALUE);
            ADVANCED_LAPPACK_TRANSFER = builder.defineInRange("ADVANCED_LAPPACK_TRANSFER", 500, 1, Integer.MAX_VALUE);
            ULTIMATE_LAPPACK_TRANSFER = builder.defineInRange("ULTIMATE_LAPPACK_TRANSFER", 4000, 1, Integer.MAX_VALUE);
            GRAVITATION_JETPACK_TRANSFER = builder.defineInRange("GRAVITATION_JETPACK_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            NUCLEAR_GRAVITATION_JETPACK_TRANSFER = builder.defineInRange("NUCLEAR_GRAVITATION_JETPACK_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            GRAVITOOL_TRANSFER = builder.defineInRange("GRAVITOOL_TRANSFER", 400, 1, Integer.MAX_VALUE);
            VAJRA_TRANSFER = builder.defineInRange("VAJRA_TRANSFER", 1000, 1 , Integer.MAX_VALUE);
            RELOCATOR_TRANSFER = builder.defineInRange("RELOCATOR_TRANSFER", 25000, 1, Integer.MAX_VALUE);
            builder.pop();
            builder.pop();
            builder.push("Misc");
            ADVANCED_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Advanced Electric jetpack charging items. Default: false")
                    .define("ADVANCED_JETPACK_PROVIDE_ENERGY", false);
            ADVANCED_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Advanced Nuclear jetpack charging items. Default: true")
                    .define("ADVANCED_N_JETPACK_PROVIDE_ENERGY", true);
            COMPACTED_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Compacted jetpack charging items. Default: false")
                    .define("COMPACTEd_JETPACK_PROVIDE_ENERGY", false);
            COMPACTED_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Compacted Nuclear jetpack charging items. Default: true")
                    .define("COMPACTEd_N_JETPACK_PROVIDE_ENERGY", true);
            GRAVITATION_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Gravitation jetpack charging items. Default: false")
                    .define("GRAVITATION_JETPACK_PROVIDE_ENERGY", false);
            GRAVITATION_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Gravitation Nuclear jetpack charging items. Default: true")
                    .define("GRAVITATION_N_JETPACK_PROVIDE_ENERGY", true);
            builder.pop();
        }
    }

    private static void bakeClientConfig() {
        CLIENT.POSITIONS = CLIENT_CONFIG.POSITONS.get();
    }

    private static void bakeCommonConfig() {
        POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_STORAGE = COMMON_CONFIG.ADVANCED_ELECTRIC_JETPACK_STORAGE.get();
        POWER_VALUES.ADVANCED_NUCLEAR_JETPACK_STORAGE = COMMON_CONFIG.ADVANCED_NUCLEAR_JETPACK_STORAGE.get();
        POWER_VALUES.ADVANCED_LAPPACK_STORAGE = COMMON_CONFIG.ADVANCED_LAPPACK_STORAGE.get();
        POWER_VALUES.ULTIMATE_LAPPACK_STORAGE = COMMON_CONFIG.ULTIMATE_LAPPACK_STORAGE.get();
        POWER_VALUES.GRAVITATION_JETPACK_STORAGE = COMMON_CONFIG.GRAVITATION_JETPACK_STORAGE.get();
        POWER_VALUES.NUCLEAR_GRAVITATION_JETPACK_STORAGE = COMMON_CONFIG.NUCLEAR_GRAVITATION_JETPACK_STORAGE.get();
        POWER_VALUES.GRAVITOOL_STORAGE = COMMON_CONFIG.GRAVITOOL_STORAGE.get();
        POWER_VALUES.VAJRA_STORAGE = COMMON_CONFIG.VAJRA_STORAGE.get();
        POWER_VALUES.RELOCATOR_STORAGE = COMMON_CONFIG.RELOCATOR_STORAGE.get();

        POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_TRANSFER = COMMON_CONFIG.ADVANCED_ELECTRIC_JETPACK_TRANSFER.get();
        POWER_VALUES.ADVANCED_NUCLEAR_JETPACK_TRANSFER = COMMON_CONFIG.ADVANCED_NUCLEAR_JETPACK_TRANSFER.get();
        POWER_VALUES.ADVANCED_LAPPACK_TRANSFER = COMMON_CONFIG.ADVANCED_LAPPACK_TRANSFER.get();
        POWER_VALUES.ULTIMATE_LAPPACK_TRANSFER = COMMON_CONFIG.ULTIMATE_LAPPACK_TRANSFER.get();
        POWER_VALUES.GRAVITATION_JETPACK_TRANSFER = COMMON_CONFIG.GRAVITATION_JETPACK_TRANSFER.get();
        POWER_VALUES.NUCLEAR_GRAVITATION_JETPACK_TRANSFER = COMMON_CONFIG.NUCLEAR_GRAVITATION_JETPACK_TRANSFER.get();
        POWER_VALUES.GRAVITOOL_TRANSFER = COMMON_CONFIG.GRAVITOOL_TRANSFER.get();
        POWER_VALUES.VAJRA_TRANSFER = COMMON_CONFIG.VAJRA_TRANSFER.get();
        POWER_VALUES.RELOCATOR_TRANSFER = COMMON_CONFIG.RELOCATOR_TRANSFER.get();

        MISC.ADVANCED_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.ADVANCED_JETPACK_PROVIDE_ENERGY.get();
        MISC.ADVANCED_N_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.ADVANCED_N_JETPACK_PROVIDE_ENERGY.get();
        MISC.COMPACTED_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.COMPACTED_JETPACK_PROVIDE_ENERGY.get();
        MISC.COMPACTED_N_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.COMPACTED_N_JETPACK_PROVIDE_ENERGY.get();
        MISC.GRAVITATION_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.GRAVITATION_JETPACK_PROVIDE_ENERGY.get();
        MISC.GRAVITATION_N_JETPACK_PROVIDE_ENERGY = COMMON_CONFIG.GRAVITATION_N_JETPACK_PROVIDE_ENERGY.get();
    }
}
