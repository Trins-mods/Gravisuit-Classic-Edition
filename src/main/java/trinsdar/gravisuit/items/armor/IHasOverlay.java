package trinsdar.gravisuit.items.armor;

import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IHasOverlay {
    // Used for GraviSuit overlay
    default boolean isEnabled(ItemStack stack){
        return true;
    }

    default CompoundTag getArmorNBT(ItemStack stack, boolean create){
        return create ? stack.getOrCreateTag() : StackUtil.getNbtData(stack);
    }
}
