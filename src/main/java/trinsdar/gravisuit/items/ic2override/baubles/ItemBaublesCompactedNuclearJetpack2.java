package trinsdar.gravisuit.items.ic2override.baubles;

import ic2.bobIntigration.core.ItemBaublesCompactedNuclearJetpack;
import net.minecraft.item.ItemStack;
import trinsdar.gravisuit.util.GravisuitConfig;

public class ItemBaublesCompactedNuclearJetpack2 extends ItemBaublesCompactedNuclearJetpack {
    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return GravisuitConfig.enableCompactedNuclearJetpackOverride;
    }
}
