package trinsdar.gravisuit.items;

import ic2.core.IC2;
import ic2.core.item.armor.electric.ItemArmorElectricJetpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
        return 100000.0D;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
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

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
    }
}
