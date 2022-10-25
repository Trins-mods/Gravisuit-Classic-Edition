package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.jetpacks.CompactedNuclearJetpack;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CompactedNuclearJetpack.class)
public abstract class CompactedNuclearJetpackMixin extends NuclearJetpack {
    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }
}
