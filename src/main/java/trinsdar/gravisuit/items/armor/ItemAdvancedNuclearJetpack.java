package trinsdar.gravisuit.items.armor;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedNuclearJetpack extends NuclearJetpack {
    public ItemAdvancedNuclearJetpack() {
        super("advanced_electric_jetpack");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"advanced_nuclear_jetpack"), this);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.ADVANCED_N_JETPACK_PROVIDE_ENERGY.get();
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.ADVANCED_NUCLEAR_JETPACK_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 0;
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

    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onInstall(stack, armor, holder);
        if (canProvideEnergy(stack)) {
            holder.addAddModifier(armor, ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onUninstall(stack, armor, holder);
        if (canProvideEnergy(stack)) {
            holder.removeAddModifier(armor, ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    @Override
    public String getTextureFolder() {
        return "jetpack";
    }

    @Override
    public String getTextureName() {
        return "advanced_nuclear_jetpack";
    }

    @Override
    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_nuclear_jetpack";
    }
}
