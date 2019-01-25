package trinsdar.gravisuit.items;

import ic2.core.item.armor.electric.ItemArmorElectricPack;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemArmorAdvancedLappack extends ItemArmorElectricPack {
    public ItemArmorAdvancedLappack() {
        super(36, "ic2:textures/models/armor/lappack", 600000, 2, 500);
        this.setRegistryName("advanced_lappack");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".advancedLappack");
    }
}
