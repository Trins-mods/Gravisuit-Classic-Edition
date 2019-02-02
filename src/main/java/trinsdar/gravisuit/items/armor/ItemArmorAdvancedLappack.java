package trinsdar.gravisuit.items.armor;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import ic2.core.item.armor.electric.ItemArmorElectricPack;
import ic2.core.item.render.model.JetpackModel;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.models.BaseModel;
import ic2.core.util.helpers.ItemWithMeta;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemArmorAdvancedLappack extends ItemArmorElectricPack implements IBauble {
    private int index;

    public ItemArmorAdvancedLappack(String reg, String un, int teir, int max, int index, int limit) {
        super(36, "gravisuit:textures/models/" + reg, max, teir, limit);
        this.index = index;
        this.setRegistryName(reg);
        this.setUnlocalizedName(GravisuitClassic.MODID + "." + un);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[index];
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
        return true;
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
