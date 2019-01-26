package trinsdar.gravisuit.items;

import ic2.core.IC2;
import ic2.core.block.base.util.info.misc.IWrench;
import ic2.core.item.armor.base.ItemArmorJetpackBase;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.item.tool.electric.ItemElectricToolHoe;
import ic2.core.item.tool.electric.ItemElectricToolPrecisionWrench;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.List;
import java.util.Map;

public class ItemToolGravitool extends ItemElectricToolPrecisionWrench{
    public static final String[] itemModes = new String[]{"Wrench", "Hoe", "Treetap"};

    public ItemToolGravitool() {
        super();
        this.setRegistryName("gravitool");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".gravitool");
        this.setCreativeTab(IC2.tabIC2);
    }

    public boolean canOverrideLossChance(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
            toolMode = toolMode.getNext();
            nbt.setByte("ToolMode", (byte)toolMode.ordinal());
            if (toolMode == ToolMode.Wrench) {
                IC2.platform.messagePlayer(player, GravisuitLang.wrench);
            } else if (toolMode == ToolMode.Hoe){
                IC2.platform.messagePlayer(player, GravisuitLang.hoe);
            } else {
                IC2.platform.messagePlayer(player, GravisuitLang.treetap);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(worldIn, player, handIn);
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(player.getHeldItem(hand));
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (toolMode == ToolMode.Wrench){
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }else if (toolMode == ToolMode.Hoe){
            return Ic2Items.electricHoe.getItem().onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }else {
            return Ic2Items.electricTreeTap.getItem().onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }

    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(player.getHeldItem(hand));
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (toolMode == ToolMode.Wrench){
            return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
        }else if (toolMode == ToolMode.Hoe){
            return Ic2Items.electricHoe.getItem().onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
        }else {
            return Ic2Items.electricTreeTap.getItem().onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(nbt.getByte("ToolMode")));
    }

    public enum ToolMode {
        Wrench,
        Hoe,
        Treetap;

        private ToolMode() {
        }

        public ToolMode getNext() {
            if (this == Wrench) {
                return Hoe;
            } else if (this == Hoe) {
                return Treetap;
            } else {
                return Wrench;
            }
        }
    }
}
