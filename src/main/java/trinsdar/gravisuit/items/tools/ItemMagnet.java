package trinsdar.gravisuit.items.tools;

import com.google.common.base.CaseFormat;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.util.Registry;

public class ItemMagnet extends IC2ElectricItem implements ISimpleItemModel {
    public ItemMagnet() {
        super("magnet");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "magnet"), this);
    }

    @Override
    protected int getEnergyCost(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.MAGNET_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.MAGNET_TRANSFER.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("magnet");
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        tick(stack, level, player);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player){
            tick(pStack, pLevel, player);
        }
    }

    private void tick(ItemStack stack, Level level, Player player) {
        if (getMagnetMode(stack) == MagnetMode.ATTRACTION && ElectricItem.MANAGER.canUse(stack, 1)){
            int range = GravisuitConfig.MAGNET_RANGE.get();
            AABB box = new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range);
            for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, box)){
                if (ElectricItem.MANAGER.canUse(stack, 1)){
                    itemEntity.playerTouch(player);
                    ElectricItem.MANAGER.use(stack, 1, player);
                } else {
                    saveMagnetMode(stack, MagnetMode.NONE);
                    break;
                }
            }
        }
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        MagnetMode mode = getMagnetMode(stack);
        helper.addSimpleToolTip(Component.translatable("item_info.toolMode", Component.translatable(mode.localeName).withStyle(mode.color)).withStyle(ChatFormatting.BLUE));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, Component.translatable("item_info.multiModes").withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (IC2.PLATFORM.isSimulating()) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
                MagnetMode nextMode = getNextMagnetMode(stack);
                saveMagnetMode(stack, nextMode);
                playerIn.displayClientMessage(Component.translatable("item_info.toolMode", Component.translatable(nextMode.localeName).withStyle(nextMode.color)).withStyle(ChatFormatting.YELLOW), false);
            }
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    public enum MagnetMode {
        NONE(ChatFormatting.AQUA), INSTANT_PICKUP(ChatFormatting.DARK_GREEN), ATTRACTION(ChatFormatting.GOLD);

        private static final MagnetMode[] VALUES = values();
        public final ChatFormatting color;
        public final String name;
        public final String localeName;

        MagnetMode(ChatFormatting color) {
            this.name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.localeName = "message.text.mode." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.color = color;
        }

        public static MagnetMode getFromId(int ID) {
            return VALUES[ID % VALUES.length];
        }
    }

    public static MagnetMode getMagnetMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return MagnetMode.getFromId(tag.getByte("mode"));
    }

    public static MagnetMode getNextMagnetMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return MagnetMode.getFromId(tag.getByte("mode") + 1);
    }

    public static void saveMagnetMode(ItemStack tool, MagnetMode mode) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        tag.putByte("mode", (byte) mode.ordinal());
    }
}