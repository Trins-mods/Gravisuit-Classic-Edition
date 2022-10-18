package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

public class ItemGraviJetpack extends IC2ElectricJetpackBase {
    public ItemGraviJetpack() {
        super("gravitation_jetpack", EquipmentSlot.CHEST, null);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"gravitation_jetpack"), this);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return 500000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 3;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 1000;
    }

    @Override
    public boolean canDoRocketMode(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isElectricJetpack(ItemStack itemStack) {
        return true;
    }

    @Override
    public float getPower(ItemStack itemStack) {
        return 1.4f;
    }

    @Override
    public float getThruster(ItemStack itemStack, HoverMode hoverMode) {
        return switch (hoverMode){
            case ADV -> 1.8f;
            case BASIC -> 1.2f;
            case NONE -> 0.6f;
        };
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return worldHeight;
    }

    @Override
    public int getMaxRocketCharge(ItemStack itemStack) {
        return 30000;
    }

    @Override
    public int getFuelCost(ItemStack itemStack, HoverMode hoverMode) {
        return switch (hoverMode){
            case NONE -> 25;
            case BASIC -> 30;
            case ADV -> 512;
        };
    }

    /*@Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, this.getTextureFolder()).get(this.getTextureName());
    }*/

    @Override
    public String getTextureFolder() {
        return "armor/jetpack";
    }

    @Override
    public String getTextureName() {
        return "electric_jetpack";
    }

    @Override
    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_electric_jetpack";
    }
}
