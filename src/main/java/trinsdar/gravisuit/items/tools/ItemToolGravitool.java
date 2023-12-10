package trinsdar.gravisuit.items.tools;


import com.google.common.base.CaseFormat;
import com.google.common.collect.Multimap;
import ic2.api.crops.ICropModifier;
import ic2.api.items.electric.ElectricItem;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.*;

import java.util.List;
import java.util.Map;

public class ItemToolGravitool extends ElectricWrenchTool implements ICropModifier, IItemModel {

    final ResourceLocation id;

    public ItemToolGravitool() {
        super("gravitool", null);
        this.tier = 2;
        this.losslessUses = -1;
        id = new ResourceLocation(GravisuitClassic.MODID,"gravitool");
        Registry.REGISTRY.put(id, this);
    }

    // Base - start
    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        ToolMode mode = getToolMode(stack);
        if (this.isImport(stack)) {
            helper.addSimpleToolTip("tooltip.item.ic2.electric_tree_tap.inv_import");
        }
        helper.addSimpleToolTip(Component.translatable("item_info.toolMode", Component.translatable(mode.localeName).withStyle(mode.color)).withStyle(ChatFormatting.BLUE));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, Component.translatable("item_info.multiModes").withStyle(ChatFormatting.GRAY)));
        if (mode == ToolMode.HOE) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.hoe.seedmode"));
        }

        /*if (GravisuitConfig.enableGravitoolRequiresLosslessPrecisionWrench && !(Loader.isModLoaded("ic2c_extras") && Ic2cExtrasCodeHelper.isOverridingLossy())){
            tooltip.add(TextFormatting.GREEN + GravisuitLang.craftingGravitool.getLocalized());
        }*/
    }

    @Override
    public ResourceLocation getRegistryName() {
        return id;
    }

    // Actions - start
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (IC2.PLATFORM.isSimulating()) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
                ToolMode nextMode = getNextToolMode(stack);
                saveToolMode(stack, nextMode);
                playerIn.displayClientMessage(Component.translatable("item_info.toolMode", Component.translatable(nextMode.localeName).withStyle(nextMode.color)).withStyle(ChatFormatting.YELLOW), false);
            }
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        ToolMode mode = getToolMode(stack);
        if (mode == ToolMode.WRENCH) {
            return super.onItemUseFirst(stack, context);
        } else if (mode == ToolMode.SCREWDRIVER) {
            return RotationHelper.rotateBlock(context.getLevel(), context.getClickedPos(), context.getPlayer() != null && (context.getPlayer().isShiftKeyDown() != context.getClickedFace().equals(Direction.DOWN))) ? InteractionResult.SUCCESS: InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ToolMode mode = getToolMode(context.getItemInHand());
        if (!IC2.KEYBOARD.isModeSwitchKeyDown(context.getPlayer())) {
            if (mode == ToolMode.WRENCH) {
                if (ModList.get().isLoaded("ae2")) {
                    return onPlayerUseBlock(context.getPlayer(), context.getLevel(), context.getHand(),
                            new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside()));
                }
                return super.useOn(context);
            } else if (mode == ToolMode.HOE) {
                return IC2Items.ELECTRIC_HOE.useOn(context);
            } else if (mode == ToolMode.TREETAP) {
                return IC2Items.ELECTRIC_TREETAP.useOn(context);
            }
        }
        return super.useOn(context);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (getToolMode(stack) == ToolMode.HOE) {
            return IC2Items.ELECTRIC_HOE.mineBlock(stack, level, state, pos, miningEntity);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (getToolMode(stack) == ToolMode.HOE) {
            boolean isHoe = state.is(BlockTags.MINEABLE_WITH_HOE);
            return super.isCorrectToolForDrops(stack, state) || isHoe;
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isCorrectToolForDrops(stack, state) ? Tiers.IRON.getSpeed() : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return getToolMode(stack) == ToolMode.HOE && ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (getToolMode(stack) == ToolMode.HOE) {
            return IC2Items.ELECTRIC_HOE.getAttributeModifiers(slot, stack);
        }
        return super.getAttributeModifiers(slot, stack);
    }

    /**
     * {@link ic2.core.item.tool.WrenchTool} - start
     * */
    @Override
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.GRAVITOOL_STORAGE;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.GRAVITOOL_TRANSFER;
    }

    @Override
    public boolean canOverrideLoss(ItemStack stack) {
        return true;
    }

    @Override
    public void onLossPrevented(Player player, ItemStack stack) {}

    @Override
    public boolean hasBigCost(ItemStack stack) {
        return false;
    }

    /**
     * {@link ICropModifier} - start
     * */

    @Override
    public boolean canChangeSeedMode(ItemStack stack) {
        return getToolMode(stack) == ToolMode.HOE;
    }

    /**
     * {@link ic2.api.items.readers.IWrenchTool} - start
     * */

    @Override
    public boolean shouldRenderOverlay(ItemStack stack) {
        return getToolMode(stack) == ToolMode.WRENCH;
    }

    // Textures

    @Override
    public List<ItemStack> getModelTypes() {
        List<ItemStack> stacks = new ObjectArrayList<>();
        for (int i = 0; i < ToolMode.values().length; i++) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putByte("mode", (byte)i);
            stacks.add(stack);
        }
        return stacks;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getSprite(ItemStack stack) {
        Map<String, TextureAtlasSprite> textures = IC2Textures.getMappedEntriesItem("gravisuit", "tools/gravitool");
        ToolMode mode = getToolMode(stack);
        return textures.get(mode.name);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity livingEntity) {
        return getToolMode(stack).ordinal() + 1;
    }

    // utils

    public boolean isImport(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("inv_import");
    }

    public static InteractionResult onPlayerUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        if (player.isSpectator() || hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (appeng.util.InteractionUtil.isInAlternateUseMode(player)) {
            BlockEntity be = level.getBlockEntity(hitResult.getBlockPos());
            if (be instanceof appeng.blockentity.AEBaseBlockEntity baseBlockEntity) {
                if (!appeng.util.Platform.hasPermissions(new appeng.api.util.DimensionalBlockPos(level, hitResult.getBlockPos()), player)) {
                    return InteractionResult.FAIL;
                }
                ElectricItem.MANAGER.use(player.getMainHandItem(), 50, player);
                return baseBlockEntity.disassembleWithWrench(player, level, hitResult);
            }
        }
        return InteractionResult.PASS;
    }

    public enum ToolMode {
        WRENCH(ChatFormatting.AQUA), HOE(ChatFormatting.DARK_GREEN), TREETAP(ChatFormatting.GOLD), SCREWDRIVER(ChatFormatting.LIGHT_PURPLE);

        private static final ToolMode[] VALUES = values();
        public final ChatFormatting color;
        public final String name;
        public final String localeName;

        ToolMode(ChatFormatting color) {
            this.name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.localeName = "message.text.mode." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.color = color;
        }

        public static ToolMode getFromId(int ID) {
            return VALUES[ID % VALUES.length];
        }
    }

    public static ToolMode getToolMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return ToolMode.getFromId(tag.getInt("mode"));
    }

    public static ToolMode getNextToolMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return ToolMode.getFromId(tag.getInt("mode") + 1);
    }

    public static void saveToolMode(ItemStack tool, ToolMode mode) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        tag.putInt("mode", mode.ordinal());
    }
}
