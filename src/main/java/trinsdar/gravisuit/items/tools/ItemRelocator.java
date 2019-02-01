package trinsdar.gravisuit.items.tools;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.BasicElectricItem;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemRelocator extends BasicElectricItem {

    public ItemRelocator() {
        this.setRegistryName("relocator");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".relocator");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
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
}
