package trinsdar.gravisuit.items.armor;

import ic2.core.item.armor.electric.ItemArmorElectricPack;
import ic2.core.platform.textures.Ic2Icons;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorAdvancedLappack extends ItemArmorElectricPack {
    private int index;
    private int tier;
    private int storage;
    private int maxTransfer;

    public ItemArmorAdvancedLappack(String name, int tier, int max, int index, int limit) {
        super(36, "gravisuit:textures/models/" + name, max, tier, limit);
        this.index = index;
        this.tier = tier;
        this.storage = max;
        this.maxTransfer = limit;
        this.setUnlocalizedName(name);
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public void setMaxStorage(int storage){
        this.storage = storage;
    }

    public void setMaxTransfer(int maxTransfer) {
        this.maxTransfer = maxTransfer;
    }

    @Override
    public int getTier(ItemStack stack) {
        return tier;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return (double)storage;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return maxTransfer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[index];
    }
}
