package trinsdar.gravisuit.items.tools;

import com.google.common.base.CaseFormat;
import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.helpers.TeleportUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.entity.PlasmaBall;
import trinsdar.gravisuit.items.container.ItemInventoryRelocator;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;

public class ItemRelocator extends IC2ElectricItem implements ISimpleItemModel, IHasHeldGui /*BasicElectricItem implements IHandHeldInventory*/ {

    public ItemRelocator() {
        super("relocator");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "relocator"), this);
    }

    @Override
    protected int getEnergyCost(ItemStack itemStack) {
        return 1000000;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.RELOCATOR_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 5;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.RELOCATOR_TRANSFER.get();
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return new ItemInventoryRelocator(player, this, itemStack, interactionHand);
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        TeleportMode mode = getTeleportMode(stack);
        helper.addSimpleToolTip(Component.translatable("item_info.toolMode", Component.translatable(mode.localeName).withStyle(mode.color)).withStyle(ChatFormatting.BLUE));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, Component.translatable("item_info.multiModes").withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(player)){
            TeleportMode nextMode = getNextTeleportMode(stack);
            saveTeleportMode(stack, nextMode);
            player.displayClientMessage(Component.translatable("message.relocator", Component.translatable(nextMode.localeName).withStyle(nextMode.color)).withStyle(ChatFormatting.YELLOW), false);
            return InteractionResultHolder.success(stack);
        }
        if (IC2.PLATFORM.isSimulating()) {
            TeleportMode mode = getTeleportMode(stack);
            if (player.isCrouching() || mode == TeleportMode.PERSONAL) {
                IC2.PLATFORM.launchGui(player, hand, null, this.getInventory(player, hand, stack));
                return InteractionResultHolder.success(stack);
            } else {
                if (nbt.contains("DefaultLocation")){
                    if (nbt.contains("Locations")){
                        CompoundTag map = nbt.getCompound("Locations");
                        String name = nbt.getString("DefaultLocation");
                        if (map.contains(name)){
                            boolean portal = mode == TeleportMode.PORTAL;
                            int use = portal ? 10000000 : 500000;
                            if (ElectricItem.MANAGER.canUse(stack, use)) {
                                PlasmaBall entity = new PlasmaBall(player.level, player, TeleportData.fromNBT(map.getCompound(name), name), hand);
                                level.addFreshEntity(entity);
                                if (portal) ElectricItem.MANAGER.use(stack, use, player);
                            } else {
                                player.sendSystemMessage(this.translate(GravisuitLang.messageRelocatorNotEnoughPower, ChatFormatting.RED));
                            }

                            return InteractionResultHolder.success(stack);
                        }
                    }
                }

            }
        }
        return super.use(level, player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("relocator");
    }

    public static void teleportEntity(Player player, CompoundTag teleportData, ItemStack stack){
        Registry.RELOCATOR.teleportEntity(player, TeleporterTarget.read(teleportData), player.getMotionDirection(), stack);
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir, ItemStack stack) {
        int weight = TeleportUtil.getWeightOfEntity(player, true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            if (ElectricItem.MANAGER.use(stack, (int)((double)weight * TeleportUtil.getDistanceCost(player.getLevel(), player.blockPosition(), server, pos) * 5.0), player)) {
                TeleportUtil.teleportEntity(player, server, pos, dir);
                IC2.AUDIO.playSound(player, new ResourceLocation("ic2", "sounds/machines/teleport.ogg"), AudioManager.SoundType.ITEM);
            } else {
                player.sendSystemMessage(this.translate(GravisuitLang.messageRelocatorNotEnoughPower, ChatFormatting.RED));
            }
        }
    }

    public enum TeleportMode {
        PERSONAL(ChatFormatting.GREEN),
        TRANSLOCATOR(ChatFormatting.GOLD),
        PORTAL(ChatFormatting.AQUA);


        private static final TeleportMode[] VALUES = values();
        public final ChatFormatting color;
        public final String name;
        public final String localeName;

        TeleportMode(ChatFormatting color) {
            this.name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.localeName = "message.text.mode." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
            this.color = color;
        }

        public static TeleportMode getFromId(int ID) {
            return VALUES[ID % VALUES.length];
        }

    }

    public static TeleportMode getTeleportMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return TeleportMode.getFromId(tag.getByte("mode"));
    }

    public static TeleportMode getNextTeleportMode(ItemStack tool) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        return TeleportMode.getFromId(tag.getByte("mode") + 1);
    }

    public static void saveTeleportMode(ItemStack tool, TeleportMode mode) {
        CompoundTag tag = StackUtil.getNbtData(tool);
        tag.putByte("mode", (byte) mode.ordinal());
    }

    public static class TeleportData {
        long pos;
        String dimId;
        String name;

        public TeleportData(long pos, String dimId, String name){
            this.pos = pos;
            this.dimId = dimId;
            this.name = name;
        }

        public TeleportData(String name){
            this.pos = 0;
            this.dimId = "minecraft:overworld";
            this.name = name;
        }

        public long getPos() {
            return pos;
        }

        public String getDimId() {
            return dimId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDimId(String dimId) {
            this.dimId = dimId;
        }

        public CompoundTag writeToNBT() {
            CompoundTag compound = new CompoundTag();
            compound.putLong("pos", pos);
            compound.putString("id", dimId);
            return compound;
        }

        public static TeleportData fromNBT(CompoundTag tag, String name){
            return new TeleportData(tag.getLong("pos"), tag.getString("id"), name);
        }

        public TeleporterTarget toTeleportTarget(){
            CompoundTag compoundTag = writeToNBT();
            return TeleporterTarget.read(compoundTag);
        }
    }
}
