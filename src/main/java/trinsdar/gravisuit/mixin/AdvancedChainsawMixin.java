package trinsdar.gravisuit.mixin;

import ic2.core.IC2;
import ic2.core.item.tool.electric.AdvancedChainsaw;
import ic2.core.item.tool.electric.ChainsawTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.GravisuitClassic;

@Mixin(AdvancedChainsaw.class)
public class AdvancedChainsawMixin extends ChainsawTool {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(CallbackInfo ci) {
        this.tier = 2;
    }

    @Redirect(method = "onBlockStartBreak", at = @At(value = "INVOKE", target = "Lic2/core/item/tool/electric/ChainsawTool;onBlockStartBreak(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)Z"), remap = false)
    private boolean redirectOnBlockStartBreak(ChainsawTool instance, ItemStack stack, BlockPos pos, Player player){
        if (!StackUtil.getNbtData(stack).getBoolean("shearing")){
            return false;
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    public boolean isMultiMining(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("treeChopping");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("advanced_chainsaw");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            boolean treeChopping = nbt.getBoolean("treeChopping");
            if (IC2.PLATFORM.isSimulating()) {
                nbt.putBoolean("treeChopping", !treeChopping);
                playerIn.displayClientMessage(this.translate("message.tree_chopping." + (!treeChopping ? "on" : "off")), false);
                return InteractionResultHolder.success(stack);
            }
        }
        if (IC2.KEYBOARD.isSneakKeyDown(playerIn)){
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            boolean shearing = nbt.getBoolean("shearing");
            if (IC2.PLATFORM.isSimulating()) {
                nbt.putBoolean("shearing", !shearing);
                playerIn.displayClientMessage(this.translate("message.shearing." + (!shearing ? "on" : "off")), false);
                return InteractionResultHolder.success(stack);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        boolean treeChopping = nbt.getBoolean("treeChopping");
        helper.addSimpleToolTip("item_info.tree_chopping_mode", this.translate("item_info." + (treeChopping ? "enabled" : "disabled")));
        boolean shearing = nbt.getBoolean("shearing");
        helper.addSimpleToolTip("item_info.shearing_mode", this.translate("item_info." + (shearing ? "enabled" : "disabled")));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, "item_info.tree_chopping_toggle"));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.SNEAK_KEY, KeyHelper.RIGHT_CLICK, "item_info.shear_toggle"));
        super.addToolTip(stack, player, type, helper);
        helper.addSimpleToolTip(Component.translatable("item_info.modified_by_gravisuit").withStyle(ChatFormatting.RED));
    }
}
