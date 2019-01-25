package trinsdar.gravisuit.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import ic2.core.IC2;
import ic2.core.item.armor.electric.ItemArmorElectricJetpack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import trinsdar.gravisuit.GravisuitClassic;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemArmorAdvancedElectricJetpack extends ItemArmorElectricJetpack implements IBauble {

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

    @Override
    @Optional.Method(modid = "baubles")
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BODY;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            this.onArmorTick(entity.getEntityWorld(), (EntityPlayer)entity, itemstack);
        }

    }


    @Override
    @Optional.Method(modid = "baubles")
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid = "baubles")
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return getArmor(itemstack).isEmpty();
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
