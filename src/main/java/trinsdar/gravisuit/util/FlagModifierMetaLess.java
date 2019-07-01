package trinsdar.gravisuit.util;

import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.core.item.recipe.upgrades.FlagModifier;
import ic2.core.util.misc.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class FlagModifierMetaLess implements ICraftingRecipeList.IRecipeModifier {
    ItemStack toCompare;
    String tag;
    boolean result;
    boolean usesInput = false;
    String tooltip;

    public FlagModifierMetaLess(ItemStack item, String id, boolean value) {
        this.toCompare = item;
        this.tag = id;
        this.result = value;
    }

    public FlagModifierMetaLess setUsesInput() {
        this.usesInput = true;
        return this;
    }

    public FlagModifierMetaLess setToolTip(String value) {
        this.tooltip = value;
        return this;
    }

    public void clear() {
    }

    public boolean isStackValid(ItemStack provided) {
        if (StackUtil.isStackEqual(this.toCompare, provided, false, false, false, true)) {
            NBTTagCompound nbt = StackUtil.getNbtData(provided);
            if (nbt.getBoolean(this.tag) == this.result) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getOutput(ItemStack output, boolean forDisplay) {
        StackUtil.getOrCreateNbtData(output).setBoolean(this.tag, this.result);
        if (forDisplay && this.tooltip != null) {
            StackUtil.addToolTip(output, TextFormatting.RESET + this.tooltip);
        }

        return output;
    }

    public boolean isOutput(ItemStack possibleOutput) {
        return this.usesInput && StackUtil.isStackEqual(this.toCompare, possibleOutput);
    }
}
