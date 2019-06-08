package trinsdar.gravisuit.items.ic2override;

import ic2.core.item.armor.electric.ItemArmorCompactedNuclearJetpack;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.Config;

public class ItemArmorCompactedNuclearJetpack2 extends ItemArmorCompactedNuclearJetpack {
    public ItemArmorCompactedNuclearJetpack2() {
        super(43);
    }
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return Config.enableCompactedNuclearJetpackOverride;
    }
}
