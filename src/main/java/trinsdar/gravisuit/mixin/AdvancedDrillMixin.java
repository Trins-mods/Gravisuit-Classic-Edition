package trinsdar.gravisuit.mixin;

import com.google.common.base.CaseFormat;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.tool.electric.AdvancedDrill;
import ic2.core.item.tool.electric.DrillTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.tools.DrillMode;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.util.GravisuitSounds;

import static trinsdar.gravisuit.items.tools.DrillMode.saveDrillMode;

@Mixin(AdvancedDrill.class)
public class AdvancedDrillMixin extends DrillTool {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(CallbackInfo ci) {
        this.tier = 2;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return super.getEnergyCost(stack) * 2;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("advanced_drill");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        DrillMode mode = getDrillMode(stack);
        helper.addSimpleToolTip(Component.translatable("item_info.toolMode", Component.translatable(mode.localeName).withStyle(mode.color)).withStyle(ChatFormatting.BLUE));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, Component.translatable("item_info.multiModes").withStyle(ChatFormatting.GRAY)));
        super.addToolTip(stack, player, type, helper);
        helper.addSimpleToolTip(Component.translatable("item_info.modified_by_gravisuit").withStyle(ChatFormatting.RED));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (IC2.PLATFORM.isSimulating()) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
                DrillMode nextMode = getNextDrillMode(stack);
                saveDrillMode(stack, nextMode);
                playerIn.displayClientMessage(Component.translatable("item_info.toolMode", Component.translatable(nextMode.localeName).withStyle(nextMode.color)).withStyle(ChatFormatting.YELLOW), false);
            }
            return InteractionResultHolder.success(stack);
        } else {
            return super.use(worldIn, playerIn, handIn);
        }
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (!ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack))) {
            return 1.0F;
        } else if (this.isCorrectToolForDrops(stack, state)) {
            return switch (getDrillMode(stack)){
                case NORMAL -> 24.0F;
                case LOW_POWER -> 16.0F;
                case FINE -> 8.0F;
                case BIG_HOLES -> 43.2f;//4.8F;
            };
        } else {
            return 1.0F;
        }
    }

    /**
     * @author Trinsdar
     * @reason In order to have proper support for the new modes
     */
    @Overwrite(remap = false)
    public boolean isMultiMining(ItemStack stack) {
        return getDrillMode(stack) == DrillMode.BIG_HOLES;
    }
    
    @Unique
    private DrillMode getDrillMode(ItemStack stack) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        boolean multimine = tag.getBoolean("multi");
        if (multimine) {
            saveDrillMode(stack, DrillMode.BIG_HOLES);
            StackUtil.getNbtData(stack).remove("multi");
        }
        return DrillMode.getFromId(tag.getByte("mode"));
    }



    @Unique
    private DrillMode getNextDrillMode(ItemStack stack) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        boolean multimine = tag.getBoolean("multi");
        if (multimine) {
            saveDrillMode(stack, DrillMode.BIG_HOLES);
            StackUtil.getNbtData(stack).remove("multi");
        }
        return DrillMode.getFromId(tag.getByte("mode") + 1);
    }
}
