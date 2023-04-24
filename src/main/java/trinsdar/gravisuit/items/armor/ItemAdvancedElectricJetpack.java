package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedElectricJetpack extends IC2ElectricJetpackBase {
    public ItemAdvancedElectricJetpack() {
        super("advanced_electric_jetpack", EquipmentSlot.CHEST, null);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"advanced_electric_jetpack"), this);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.ADVANCED_JETPACK_PROVIDE_ENERGY;
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_STORAGE;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_TRANSFER;
    }

    @Override
    public boolean canDoRocketMode(ItemStack itemStack) {
        return false;
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
        return 1;
    }

    @Override
    public float getThruster(ItemStack itemStack, HoverMode hoverMode) {
        return switch (hoverMode){
            case ADV -> 1.35f;
            case BASIC -> 0.9f;
            case NONE -> 0.45f;
        };
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0.05f;
    }

    @Override
    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return (int)((float)worldHeight / 1.15F);
    }

    @Override
    public int getMaxRocketCharge(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getFuelCost(ItemStack itemStack, HoverMode hoverMode) {
        return hoverMode == HoverMode.BASIC ? 8 : 14;
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
