package trinsdar.gravisuit.items.ic2override;

import ic2.core.item.armor.electric.ItemArmorCompactedElectricJetpack;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.Config;

public class ItemArmorCompactedElectricJetpack2 extends ItemArmorCompactedElectricJetpack {
    public ItemArmorCompactedElectricJetpack2() {
        super(42);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return Config.enableCompactedElectricJetpackOverride;
    }
}
