package trinsdar.gravisuit.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.Registry;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "playerDestroy",at = @At("HEAD"))
    private void injectSetSilkTouch(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool, CallbackInfo callbackInfo){
        if (tool.getItem() == Registry.VAJRA){
            CompoundTag tag = tool.getTag();
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(tool);
            if (tag != null && tag.getBoolean("silkTouch")){
                enchantments.put(Enchantments.SILK_TOUCH, 1);
                EnchantmentHelper.setEnchantments(enchantments, tool);
            }
        }
    }

    @Inject(method = "playerDestroy",at = @At("TAIL"))
    private void injectRemoveSilkTouch(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool, CallbackInfo callbackInfo){
        if (tool.getItem() == Registry.VAJRA){
            CompoundTag tag = tool.getTag();
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(tool);
            if (tag != null && tag.getBoolean("silkTouch")){
                enchantments.remove(Enchantments.SILK_TOUCH);
                EnchantmentHelper.setEnchantments(enchantments, tool);
            }
        }
    }
}
