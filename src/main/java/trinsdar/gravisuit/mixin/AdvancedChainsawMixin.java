package trinsdar.gravisuit.mixin;

import ic2.core.IC2;
import ic2.core.item.tool.electric.AdvancedChainsaw;
import ic2.core.item.tool.electric.ChainsawTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancedChainsaw.class)
public class AdvancedChainsawMixin extends ChainsawTool {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(CallbackInfo ci) {
        this.tier = 2;
    }

    public boolean isMultiMining(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("treeChopping");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = StackUtil.getNbtData(stack);
            boolean treeChopping = nbt.getBoolean("treeChopping");
            if (IC2.PLATFORM.isSimulating()) {
                nbt.putBoolean("treeChopping", !treeChopping);
                playerIn.displayClientMessage(this.translate("message.tree_chopping." + (!treeChopping ? "on" : "off")), false);
                return InteractionResultHolder.success(stack);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        boolean silkTouch = nbt.getBoolean("treeChopping");
        helper.addSimpleToolTip("item_info.tree_chopping_mode", this.translate("item_info." + (silkTouch ? "enabled" : "disabled")));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "item_info.tree_chopping_toggle"));
        super.addToolTip(stack, player, type, helper);
        helper.addSimpleToolTip(Component.translatable("item_info.modified_by_gravisuit").withStyle(ChatFormatting.RED));
    }
}
