package trinsdar.gravisuit.items.ic2override;

import ic2.core.item.armor.electric.ItemArmorQuantumNuclearJetplate;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.GravisuitConfig;

public class ItemArmorQuantumNuclearJetplate2 extends ItemArmorQuantumNuclearJetplate {
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return GravisuitConfig.enableQuantumNuclearJetplateOverride;
    }
}
