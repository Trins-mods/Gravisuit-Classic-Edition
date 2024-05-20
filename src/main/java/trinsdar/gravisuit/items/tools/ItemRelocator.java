package trinsdar.gravisuit.items.tools;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(player)){
            byte mode = nbt.getByte("mode");
            if (mode == 2) {
                nbt.putByte("mode", (byte) 0);
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorPersonal, ChatFormatting.GREEN), false);
            } else if (mode == 0){
                nbt.putByte("mode", (byte) 1);
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorTranslocator, ChatFormatting.GOLD), false);
            } else {
                nbt.putByte("mode", (byte) 2);
                player.displayClientMessage(this.translate(GravisuitLang.messageRelocatorPortal, ChatFormatting.AQUA), false);
            }
            return InteractionResultHolder.success(stack);
        }
        if (IC2.PLATFORM.isSimulating())
            if (player.isCrouching() || nbt.getByte("mode") == 0) {
                IC2.PLATFORM.launchGui(player, hand, null, this.getInventory(player, hand, stack));
                return InteractionResultHolder.success(stack);
            } else if (nbt.getByte("mode") >= 1){
                if (nbt.contains("DefaultLocation")){
                    if (nbt.contains("Locations")){
                        CompoundTag map = nbt.getCompound("Locations");
                        String name = nbt.getString("DefaultLocation");
                        if (map.contains(name)){
                            boolean portal = nbt.getByte("mode") == 2;
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
        PERSONAL,
        PORTAL,
        TRANSLOCATOR;

        public TeleportMode getNext() {
            return switch (this){
                case PERSONAL -> PORTAL;
                case PORTAL -> TRANSLOCATOR;
                case TRANSLOCATOR -> PERSONAL;
            };
        }
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
