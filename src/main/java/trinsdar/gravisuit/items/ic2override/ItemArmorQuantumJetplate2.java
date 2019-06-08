package trinsdar.gravisuit.items.ic2override;

import ic2.core.item.armor.electric.ItemArmorQuantumJetplate;
import ic2.core.item.armor.electric.ItemArmorQuantumNuclearJetplate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.Config;

public class ItemArmorQuantumJetplate2 extends ItemArmorQuantumJetplate {
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return Config.enableQuantumJetplateOverride;
    }
}
