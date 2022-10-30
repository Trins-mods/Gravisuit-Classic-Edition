package trinsdar.gravisuit.items.tools;


import com.google.common.collect.Multimap;
import ic2.api.crops.ICropModifier;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.tool.electric.ElectricWrenchTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.RotationHelper;

import java.util.List;
import java.util.Map;

public class ItemToolGravitool extends ElectricWrenchTool implements ICropModifier, IItemModel {
    final ResourceLocation id;

    public ItemToolGravitool() {
        super("gravitool", null);
        this.capacity = GravisuitConfig.POWER_VALUES.GRAVITOOL_STORAGE;
        this.transferLimit = GravisuitConfig.POWER_VALUES.GRAVITOOL_TRANSFER;
        this.tier = 2;
        this.losslessUses = -1;
        id = new ResourceLocation(GravisuitClassic.MODID,"gravitool");
        Registry.REGISTRY.put(id, this);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return id;
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public void setMaxCharge(int storage){
        this.capacity = storage;
    }

    public void setMaxTransfer(int maxTransfer) {
        this.transferLimit = maxTransfer;
    }

    @Override
    public boolean canOverrideLoss(ItemStack stack) {
        return true;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            byte mode = nbt.getByte("mode");
            if (mode == 3) {
                nbt.putByte("mode", (byte) 0);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageWrench, ChatFormatting.AQUA), false);
            } else if (mode == 0){
                nbt.putByte("mode", (byte) 1);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageHoe, ChatFormatting.GOLD), false);
            } else if (mode == 1){
                nbt.putByte("mode", (byte) 2);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageTreetap, ChatFormatting.DARK_GREEN), false);
            }else {
                nbt.putByte("mode", (byte) 3);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageScrewdriver, ChatFormatting.LIGHT_PURPLE), false);
            }
            return InteractionResultHolder.success(stack);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if (!IC2.KEYBOARD.isModeSwitchKeyDown(context.getPlayer())) {
            if (getMode(stack) == 2) {
                return IC2Items.ELECTRIC_TREETAP.useOn(context);
            } else if (getMode(stack) == 1){
                return IC2Items.ELECTRIC_HOE.useOn(context);
            }
        }
        return super.useOn(context);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (getMode(stack) == 1){
            return IC2Items.ELECTRIC_HOE.mineBlock(stack, level, state, pos, miningEntity);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (getMode(stack) == 1){
            boolean isHoe = state.is(BlockTags.MINEABLE_WITH_HOE);
            return super.isCorrectToolForDrops(stack, state) || isHoe;
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isCorrectToolForDrops(stack, state) ? Tiers.IRON.getSpeed() : super.getDestroySpeed(stack, state);
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction)){
            return getMode(stack) == 1;
        }
        return super.canPerformAction(stack, toolAction);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (getMode(stack) == 1){
            return IC2Items.ELECTRIC_HOE.getAttributeModifiers(slot, stack);
        }
        return super.getAttributeModifiers(slot, stack);
    }

    public byte getMode(ItemStack stack){
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getByte("mode");
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (getMode(stack) == 0) {
            return super.onItemUseFirst(stack, context);
        } else if (getMode(stack) == 3) {
            return RotationHelper.rotateBlock(context.getLevel(), context.getClickedPos(), context.getPlayer()!=null && (context.getPlayer().isShiftKeyDown() != context.getClickedFace().equals(Direction.DOWN))) ? InteractionResult.SUCCESS: InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this.getMode(stack) == 0){
            helper.addSimpleToolTip(GravisuitLang.toolMode, Component.translatable(GravisuitLang.wrench));
        }else if (this.getMode(stack) == 1){
            helper.addSimpleToolTip(GravisuitLang.toolMode, Component.translatable(GravisuitLang.hoe));
        }else if (this.getMode(stack) == 2){
            helper.addSimpleToolTip(GravisuitLang.toolMode, Component.translatable(GravisuitLang.treetap));
        }else if (this.getMode(stack) == 3){
            helper.addSimpleToolTip(GravisuitLang.toolMode, Component.translatable(GravisuitLang.screwdriver));
        }
        if (this.isImport(stack)) {
            helper.addSimpleToolTip("tooltip.item.ic2.electric_tree_tap.inv_import", new Object[0]);
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.hoe.seedmode", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, GravisuitLang.multiModes, new Object[0]));

        /*if (GravisuitConfig.enableGravitoolRequiresLosslessPrecisionWrench && !(Loader.isModLoaded("ic2c_extras") && Ic2cExtrasCodeHelper.isOverridingLossy())){
            tooltip.add(TextFormatting.GREEN + GravisuitLang.craftingGravitool.getLocalized());
        }*/
    }

    @Override
    public void onLossPrevented(Player player, ItemStack stack) {
    }

    @Override
    public boolean hasBigCost(ItemStack stack) {
        return false;
    }

    public boolean isImport(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("inv_import");
    }

    @Override
    public boolean canChangeSeedMode(ItemStack itemStack) {
        return getMode(itemStack) == 1;
    }

    @Override
    public boolean shouldRenderOverlay(ItemStack stack) {
        return getMode(stack) == 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        List<ItemStack> stacks = new ObjectArrayList<>();
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putByte("mode", (byte)i);
            stacks.add(stack);
        }
        return stacks;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getSprite(ItemStack itemStack) {
        Map<String, TextureAtlasSprite> textures = IC2Textures.getMappedEntriesItem("gravisuit", "tools/gravitool");
        int mode = getMode(itemStack);
        return textures.get(mode == 0 ? "wrench" : (mode == 1 ? "hoe" : (mode == 2 ? "treetap" : "screwdriver")));
    }

    @Override
    public int getModelIndexForStack(ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return getMode(itemStack) + 1;
    }
}
