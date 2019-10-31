package trinsdar.gravisuit.items.ic2override;

import ic2.core.item.armor.electric.ItemArmorQuantumJetplate;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.GravisuitConfig;

public class ItemArmorQuantumJetplate2 extends ItemArmorQuantumJetplate {
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return GravisuitConfig.enableQuantumJetplateOverride;
    }
}
