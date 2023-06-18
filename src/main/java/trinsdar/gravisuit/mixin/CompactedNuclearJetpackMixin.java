package trinsdar.gravisuit.mixin;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.jetpacks.CompactedNuclearJetpack;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.util.GravisuitConfig;

@Mixin(CompactedNuclearJetpack.class)
public abstract class CompactedNuclearJetpackMixin extends NuclearJetpack {
    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.COMPACTED_N_JETPACK_PROVIDE_ENERGY;
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
}
