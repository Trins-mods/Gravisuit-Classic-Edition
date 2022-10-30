package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import trinsdar.gravisuit.items.armor.IHasOverlay;

@Mixin(IC2ElectricJetpackBase.class)
public abstract class IC2ElectricJetpackBaseMixin implements IHasOverlay {
    @Shadow public abstract CompoundTag getNBTData(ItemStack stack, boolean create);

    @Override
    public CompoundTag getArmorNBT(ItemStack stack, boolean create) {
        return getNBTData(stack, create);
    }
}
