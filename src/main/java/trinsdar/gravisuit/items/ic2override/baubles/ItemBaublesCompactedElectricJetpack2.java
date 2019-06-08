package trinsdar.gravisuit.items.ic2override.baubles;

import ic2.bobIntigration.core.ItemBaublesCompactedElectricJetpack;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.Config;

public class ItemBaublesCompactedElectricJetpack2 extends ItemBaublesCompactedElectricJetpack {
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return Config.enableCompactedElectricJetpackOverride;
    }
}
