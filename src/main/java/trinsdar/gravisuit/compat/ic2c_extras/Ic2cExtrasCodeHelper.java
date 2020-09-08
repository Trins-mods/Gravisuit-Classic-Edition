package trinsdar.gravisuit.compat.ic2c_extras;

import trinsdar.ic2c_extras.Ic2cExtrasConfig;

public class Ic2cExtrasCodeHelper {

    public static boolean isOverridingLossy(){
        return Ic2cExtrasConfig.removeLossyWrenchMechanic;
    }
}
