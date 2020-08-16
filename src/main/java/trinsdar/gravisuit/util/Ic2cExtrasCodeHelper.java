package trinsdar.gravisuit.util;

import trinsdar.ic2c_extras.Ic2cExtrasConfig;

public class Ic2cExtrasCodeHelper {

    public static boolean isOverridingLossy(){
        return Ic2cExtrasConfig.removeLossyWrenchMechanic;
    }
}
