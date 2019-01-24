package trinsdar.gravisuit.items;

import ic2.core.IC2;
import ic2.core.item.armor.electric.ItemArmorElectricJetpack;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;

public class ItemArmorAdvancedElectricJetpack extends ItemArmorElectricJetpack {

    public ItemArmorAdvancedElectricJetpack(){
        super();
        this.setRegistryName("advanced_electric_jetpack");
        this.setUnlocalizedName(GravisuitClassic.MODID +".advancedElectricJetpack");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return 60000.0D;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 120.0D;
    }

    @Override
    public int getMaxHeight(ItemStack stack, int worldHeight) {
        return (int)((float)worldHeight / 1.024F);
    }

    @Override
    public String getTexture() {
        return "ic2:textures/models/armor/jetpack_Electric";
    }
}
