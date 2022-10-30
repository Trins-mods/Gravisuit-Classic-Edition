package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.items.armor.IHasOverlay;

@Mixin(ElectricPackArmor.class)
public class ElectricPackArmorMixin implements IHasOverlay {
}
