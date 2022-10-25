package trinsdar.gravisuit.util;

import net.minecraftforge.fml.config.ModConfig;
import trinsdar.gravisuit.GravisuitClassic;

public class GravisuitConfig {
    private static final String CATEGORY_POWER_VALUES = "power values";
    private static final String CATEGORY_ENABLED_ITEMS = "enabled items";
    private static final String CATEGORY_MISC = "misc";

    //public static final ClientConfig CLIENT_CONFIG;
    //public static final ForgeConfigSpec CLIENT_SPEC;
    //public static final CommonConfig COMMON_CONFIG;
    //public static final ForgeConfigSpec COMMON_SPEC;

    static {

        //final Pair<CommonConfig, ForgeConfigSpec> COMMON_PAIR = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        //final Pair<ClientConfig, ForgeConfigSpec> CLIENT_PAIR = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        //CLIENT_CONFIG = CLIENT_PAIR.getLeft();
        //CLIENT_SPEC = CLIENT_PAIR.getRight();
        //COMMON_CONFIG = COMMON_PAIR.getLeft();
        //COMMON_SPEC = COMMON_PAIR.getRight();

    }

    public static void onModConfigEvent(final ModConfig e) {
        if (e.getModId().equals(GravisuitClassic.MODID)){
            //if (e.getSpec() == CLIENT_SPEC) bakeClientConfig();
            //else if (e.getSpec() == COMMON_SPEC) bakeCommonConfig();
        }
    }

    //@Comment(value = "Set the max EU storage and max EU transfer of each item here.")
    //@Name(value = "power values")
    public static PowerValues powerValues = new PowerValues();

    public static class General{
        //@Comment("Enable or Disable the compacted electric jetpack charging items like a lappack does here.")
        //@RequiresMcRestart
        public boolean ENABLE_COMPACTED_ELECTRIC_JETPACK_OVERRIDE;
        //@Comment("Enable or Disable the compacted nuclear jetpack charging items like a lappack does here.")
        //@RequiresMcRestart
        public boolean ENABLE_COMPACTED_NUCLEAR_JETPACK_OVERRIDE;
        //@Comment("Enable or Disable the overriding of compacted jetpack and jetplate recipes here. Also requires that the configs for making them charge items also be enabled.")
        //@RequiresMcRestart
        public boolean ENABLE_IC2_JETPACK_RECIPE_OVERRIDES;
        //@Comment("Enable or Disable the gravitool requiring the completly lossless version of the precision wrench here.")
        //@RequiresMcRestart
        public boolean ENABLE_GRAVITOOL_REQUIRES_LOSSLESS_PRECISION_WRENCH;
    }

    public static class PowerValues {

        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedElectricJetpackStorage = 500000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNuclearJetpackStorage = 500000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedLappackStorage = 600000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int ultimateLappackStorage = 10000000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNanoChestplateStorage = 600000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNuclearNanoChestplateStorage = 600000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int gravisuitStorage = 10000000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int nuclearGravisuitStorage = 10000000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedChainsawStorage = 100000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedDrillStorage = 100000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int gravitoolStorage = 50000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int vajraStorage = 3000000;

        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedElectricJetpackTransfer = 500;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNuclearJetpackTransfer = 500;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedLappackTransfer = 500;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int ultimateLappackTransfer = 4000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNanoChestplateTransfer = 500;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedNuclearNanoChestplateTransfer = 500;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int gravisuitTransfer = 5000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int nuclearGravisuitTransfer = 5000;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedChainsawTransfer = 200;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int advancedDrillTransfer = 200;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int gravitoolTransfer = 400;
        //@RangeInt(min = 1)
        //@RequiresMcRestart
        public int vajraTransfer = 1000;
    }

    //@Comment(value = "Enable or Disable each item here.")
    //@Name(value = "enabled items")
    public static EnabledItems enabledItems = new EnabledItems();

    public static class EnabledItems {

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

    //@Comment("Client only stuff.")
    public static Client client = new Client();

    public static class Client {
        //@Comment("Set the location of the hud position here.")
        public Positions positions = Positions.TOPLEFT;

        public enum Positions {
            TOPLEFT, TOPRIGHT, TOPMIDDLE, BOTTOMLEFT, BOTTOMRIGHT
        }
    }
}

