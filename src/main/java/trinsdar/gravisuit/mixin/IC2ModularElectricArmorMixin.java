package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import trinsdar.gravisuit.items.armor.IHasOverlay;

import java.util.Iterator;

@Debug(export = true)
@Mixin(IC2ModularElectricArmor.class)
public abstract class IC2ModularElectricArmorMixin implements IHasOverlay {
    @Shadow public abstract IC2JetpackBase getJetpack(ItemStack stack);

    @Override
    public boolean isEnabled(ItemStack stack) {
        ListTag listTag = StackUtil.getNbtData(stack).getList("armor_upgrades", 10);

        for (int i = 0; i < listTag.size(); i++){
            CompoundTag nbt = listTag.getCompound(i);
            Item item = ItemStack.of(nbt).getItem();
            if (item instanceof ElectricPackArmor){
                return true;
            }
        }
        return getJetpack(stack) != null;
    }

    @Override
    public CompoundTag getArmorNBT(ItemStack stack, boolean create) {
        CompoundTag data = create ? stack.getOrCreateTag() : StackUtil.getNbtData(stack);
        CompoundTag subData = data.getCompound("jetpack_data");
        if (create) {
            data.put("jetpack_data", subData);
        }
        return subData;
    }
}
