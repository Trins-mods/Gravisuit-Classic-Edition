package trinsdar.gravisuit.items.tools;

import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.tool.electric.PortableTeleporter;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
        return GravisuitConfig.POWER_VALUES.RELOCATOR_STORAGE;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 5;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.RELOCATOR_TRANSFER;
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
            } else if (nbt.getByte("mode") == 1){
                if (nbt.contains("DefaultLocation")){
                    if (nbt.contains("Locations")){
                        CompoundTag map = nbt.getCompound("Locations");
                        String name = nbt.getString("DefaultLocation");
                        if (map.contains(name)){
                            PlasmaBall entity = new PlasmaBall(player.level, player, TeleportData.fromNBT(map.getCompound(name), name), hand);
                            level.addFreshEntity(entity);
                            return InteractionResultHolder.success(stack);
                        }
                    }
                }

            }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        CompoundTag nbt = stack.getOrCreateTag();
        if (IC2.PLATFORM.isSimulating()){
            if (nbt.getByte("mode") == 2){
                if (nbt.contains("DefaultLocation")){
                    if (nbt.contains("Locations")){
                        CompoundTag map = nbt.getCompound("Locations");
                        String name = nbt.getString("DefaultLocation");
                        if (map.contains(name)){
                            context.getLevel().setBlock(context.getClickedPos().relative(context.getClickedFace()), Registry.PLASMA_PORTAL.defaultBlockState(), 3);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.useOn(context);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("relocator");
    }

    public static void teleportEntity(Player player, CompoundTag teleportData, ItemStack stack){
        ((PortableTeleporter)IC2Items.PORTABLE_TELEPORTER).teleportEntity(player, TeleporterTarget.read(teleportData), player.getMotionDirection(), stack);
    }

    /* @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        if (IC2.platform.isSimulating()) {
            if (IC2.keyboard.isModeSwitchKeyDown(player)){
                NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
                TeleportMode teleportMode = TeleportMode.values()[nbt.getByte("TeleportMode")];
                teleportMode = teleportMode.getNext();
                nbt.setByte("TeleportMode", (byte)teleportMode.ordinal());
                if (teleportMode == TeleportMode.PERSONAL) {
                    IC2.platform.messagePlayer(player, GravisuitLang.messageRelocatorPersonal);
                } else if (teleportMode == TeleportMode.PORTAL){
                    IC2.platform.messagePlayer(player, GravisuitLang.messageRelocatorPortal);
                }else {
                    IC2.platform.messagePlayer(player, GravisuitLang.messageRelocatorTranslocator);
                }
            } else {
                if (IC2.platform.isSimulating()) {
                    NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
                    if (nbt.getByte("TeleportMode") == 0 || player.isSneaking()) {
                        IC2.platform.launchGui(player, this.getInventory(player, handIn, player.getHeldItem(handIn)), handIn);
                    }
                }else {
                    return super.onItemRightClick(worldIn, player, handIn);
                }
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(worldIn, player, handIn);
        }
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        super.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        byte teleportMode = nbt.getByte("TeleportMode");
        if (teleportMode == 0){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.relocatorPersonal));
        }else if (teleportMode == 1){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.relocatorPortal));
        }else if (teleportMode == 2){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.relocatorTranslocator));
        }
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), GravisuitLang.multiModes.getLocalized()));
        tooltip.add(TextFormatting.RED + I18n.format("tooltip." + GravisuitClassic.MODID + ".wip"));
    }

    @Override
    public int getTextureEntry(int i) {
        return 179;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return 10.0E7D;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 5;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 50000.0D;
    }


    public boolean canUse(ItemStack stack, int energy) {
        return ElectricItem.manager.canUse(stack, (double)(energy));
    }

    public void useEnergy(ItemStack stack, int energy, EntityPlayer player) {
        ElectricItem.manager.use(stack, (double)(energy), player);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    public IHasGui getInventory(EntityPlayer entityPlayer, EnumHand enumHand, ItemStack itemStack) {
        return new ItemInventoryRelocator(entityPlayer, this, itemStack, enumHand);
    }

    @Override
    public int getGuiId(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        if (nbt.hasKey("GuiID")) {
            return nbt.getInteger("GuiID");
        }
        return -1;
    }

    @Override
    public void setGuiID(ItemStack stack, int id) {
        if (id == -1) {
            StackUtil.getOrCreateNbtData(stack).removeTag("GuiID");
            return;
        }
        StackUtil.getOrCreateNbtData(stack).setInteger("GuiID", id);
    }

    public static void onButtonClick(ItemStack stack, int function, EntityPlayer player, TeleportData data){
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        NBTTagCompound map = nbt.getCompoundTag("Locations");
        if (function == 0){
            NBTTagCompound tag = new NBTTagCompound();
            data.writeToNBT(tag);
            map.setTag(data.getName(), tag);
            nbt.setTag("Locations", map);
        }
    }

    public static void teleportEntity(EntityPlayer player, int x, int y, int z, int dimId, ItemStack stack) {
        int weight = TileEntityTeleporter.getWeightOfUser(player);
        if (weight != 0) {
            double distance = Math.sqrt(player.getPosition().distanceSq(new BlockPos(x, y, z)));
            boolean dimSwitch = player.world.provider.getDimension() != dimId;
            int energyCost = (int)((double)weight * Math.pow(distance + 10.0D, dimSwitch ? 0.9D : 0.7D) * 6.0D);
            if (ElectricItem.manager.use(stack, (double)energyCost, player)) {
                player.dismountRidingEntity();
                player.removePassengers();
                IC2.achievements.issueStat(player, "distanceTeleported", (int)distance);
                if (distance >= 1000.0D) {
                    IC2.achievements.issueStat(player, "teleportFarAway");
                }

                BlockPos targetPos = new BlockPos(x, y, z);


                if (!IC2.config.getFlag("TeleporterInventory")) {
                    player.inventory.dropAllItems();
                }

                if (dimSwitch) {
                    player.setPositionAndRotation(x, y, z, player.rotationYaw, player.rotationPitch);
                } else {
                    player.setPositionAndUpdate(x, y, z);
                }

                if (dimSwitch) {
                    TileEntityTeleporter.transferPlayerToDimension((EntityPlayerMP)player, dimId);
                }

            }
        }
    }*/

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

        public CompoundTag writeToNBT(CompoundTag compound) {
            compound.putLong("pos", pos);
            compound.putString("id", dimId);
            return compound;
        }

        public static TeleportData fromNBT(CompoundTag tag, String name){
            return new TeleportData(tag.getLong("pos"), tag.getString("id"), name);
        }

        public TeleporterTarget toTeleportTarget(){
            CompoundTag compoundTag = writeToNBT(new CompoundTag());
            return TeleporterTarget.read(compoundTag);
        }
    }
}
