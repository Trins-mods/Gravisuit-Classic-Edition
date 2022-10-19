package trinsdar.gravisuit.items.armor;

import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.jetpacks.CompactElectricJetpack;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedElectricJetpack extends IC2ElectricJetpackBase {
    public ItemAdvancedElectricJetpack() {
        super("advanced_electric_jetpack", EquipmentSlot.CHEST, null);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"advanced_electric_jetpack"), this);
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
        return 2;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 500;
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
        return 0;
    }

    @Override
    public float getThruster(ItemStack itemStack, HoverMode hoverMode) {
        return 0;
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return (int)((float)worldHeight / 1.05F);
    }

    @Override
    public int getMaxRocketCharge(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getFuelCost(ItemStack itemStack, HoverMode hoverMode) {
        return 0;
    }

    @Override
    public String getTextureFolder() {
        return "jetpack";
    }

    @Override
    public String getTextureName() {
        return "advanced_electric_jetpack";
    }

    @Override
    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_electric_jetpack";
    }
}
