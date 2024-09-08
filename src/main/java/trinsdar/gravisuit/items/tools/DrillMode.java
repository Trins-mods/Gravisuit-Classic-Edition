package trinsdar.gravisuit.items.tools;

import com.google.common.base.CaseFormat;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public enum DrillMode {
    NORMAL(ChatFormatting.AQUA), LOW_POWER(ChatFormatting.DARK_GREEN), FINE(ChatFormatting.GOLD), BIG_HOLES(ChatFormatting.LIGHT_PURPLE);

    private static final DrillMode[] VALUES = values();
    public final ChatFormatting color;
    public final String name;
    public final String localeName;

    DrillMode(ChatFormatting color) {
        this.name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
        this.localeName = "message.text.mode." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
        this.color = color;
    }

    public static DrillMode getFromId(int ID) {
        return VALUES[ID % VALUES.length];
    }

    public static DrillMode getDrillMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return DrillMode.getFromId(tag.getByte("mode"));
    }

    public static DrillMode getNextDrillMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return DrillMode.getFromId(tag.getByte("mode") + 1);
    }

    public static void saveDrillMode(ItemStack tool, DrillMode mode) {
        CompoundTag tag = tool.getOrCreateTag();
        tag.putByte("mode", (byte) mode.ordinal());
    }
}
