package trinsdar.gravisuit.items.armor;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.logic.TickableItemLogic;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemNuclearGravitationJetpack extends NuclearJetpack implements IGravitationJetpack {
    public ItemNuclearGravitationJetpack() {
        super("nuclear_gravitation_jetpack");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"nuclear_gravitation_jetpack"), this);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.GRAVITATION_N_JETPACK_PROVIDE_ENERGY.get();
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.NUCLEAR_GRAVITATION_JETPACK_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 3;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 0;
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
            case ADV -> 40;
        };
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (this.armorTick(stack, world, player)) {
            super.onArmorTick(stack, world, player);
        } else {
            TickableItemLogic logic = this.getLogic(stack, player);
            if (logic != null) {
                logic.onTick(stack);
                logic.save(stack);
            }
        }
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
        return "nuclear_gravitation_jetpack";
    }

    @Override
    public String getArmorTexture() {
        return "gravisuit:textures/models/nuclear_gravitation_jetpack";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        toolTip(stack, player, type, helper);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(armor, stack, player, type, helper);
        toolTip(armor, player, type, helper);
    }

    @Override
    public CompoundTag nbtData(ItemStack stack, boolean create) {
        return getNBTData(stack, create);
    }
}
