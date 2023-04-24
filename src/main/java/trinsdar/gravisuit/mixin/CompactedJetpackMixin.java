package trinsdar.gravisuit.mixin;

import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.jetpacks.CompactElectricJetpack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.util.GravisuitConfig;

@Mixin(CompactElectricJetpack.class)
public abstract class CompactedJetpackMixin extends IC2ElectricJetpackBase {
    public CompactedJetpackMixin(String itemName, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(itemName, slot, props);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.COMPACTED_JETPACK_PROVIDE_ENERGY;
    }
}
