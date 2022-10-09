package trinsdar.gravisuit.items.tools;

import ic2.core.utils.IC2ItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.Registry;

public class ItemRelocator extends Item /*BasicElectricItem implements IHandHeldInventory*/ {

    public ItemRelocator() {
        super(new Item.Properties().tab(IC2ItemGroup.TAB_TOOLS));
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "relocator"), this);
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
    }

    public enum TeleportMode {
        PERSONAL,
        PORTAL,
        TRANSLOCATOR;

        private TeleportMode() {
        }

        public TeleportMode getNext() {
            if (this == PERSONAL) {
                return PORTAL;
            } else if (this == PORTAL) {
                return TRANSLOCATOR;
            } else {
                return PERSONAL;
            }
        }
    }

    public static class TeleportData {
        int x;
        int y;
        int z;
        int dimId;
        String name;

        public TeleportData(int x, int y, int z, int dimId, String name){
            this.x = x;
            this.y = y;
            this.z = z;
            this.dimId = dimId;
            this.name = name;
        }

        public TeleportData(String name){
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.dimId = 0;
            this.name = name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getDimId() {
            return dimId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public void setDimId(int dimId) {
            this.dimId = dimId;
        }

        public void writeToNBT(NBTTagCompound compound) {
            compound.setDouble("X", x);
            compound.setDouble("Y", y);
            compound.setDouble("Z", z);
            compound.setInteger("Dimension", dimId);
        }
    }*/
}
