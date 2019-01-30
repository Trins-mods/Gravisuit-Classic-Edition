package trinsdar.gravisuit.items.tools;

import buildcraft.api.tools.IToolWrench;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.block.base.util.info.misc.IWrench;
import ic2.core.item.armor.base.ItemArmorJetpackBase;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.item.tool.electric.ItemElectricToolHoe;
import ic2.core.item.tool.electric.ItemElectricToolPrecisionWrench;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.ITexturedItem;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.api.ICustomToolHandler;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.List;
import java.util.Map;

@Optional.Interface(iface = "reborncore.api.ICustomToolHandler", modid = "techreborn", striprefs = true)
@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore", striprefs = true)
@Optional.Interface(iface = "mrtjp.projectred.api.IScrewdriver", modid = "projectred-core", striprefs = true)
public class ItemToolGravitool extends ItemElectricToolPrecisionWrench implements ICustomToolHandler, IToolWrench, IScrewdriver, ITexturedItem {

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
                IC2.platform.messagePlayer(player, GravisuitLang.messageWrench);
            } else if (toolMode == ToolMode.Hoe){
                IC2.platform.messagePlayer(player, GravisuitLang.messageHoe);
            } else if (toolMode == ToolMode.Treetap){
                IC2.platform.messagePlayer(player, GravisuitLang.messageTreetap);
            }else {
                IC2.platform.messagePlayer(player, GravisuitLang.messageScrewdriver);
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
        }else {
            return EnumActionResult.PASS;
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[9];
    }

    @Override
    public List<ItemStack> getValidItemVariants() {
        return new ArrayList();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite getTexture(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        if (nbt.getByte("ToolMode") == 0){
            return Ic2Icons.getTextures("gravisuit_items")[9];
        }else if (nbt.getByte("ToolMode") == 1){
            return Ic2Icons.getTextures("gravisuit_items")[10];
        }else if (nbt.getByte("ToolMode") == 2){
            return Ic2Icons.getTextures("gravisuit_items")[11];
        }else {
            return Ic2Icons.getTextures("gravisuit_items")[12];
        }
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(player.getHeldItem(hand));
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (toolMode == ToolMode.Hoe){
            return Ic2Items.electricHoe.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else if (toolMode == ToolMode.Treetap){
            return Ic2Items.electricTreeTap.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (toolMode == ToolMode.Wrench){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.wrench));
        }else if (toolMode == ToolMode.Hoe){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.hoe));
        }else if (toolMode == ToolMode.Treetap){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.treetap));
        }else if (toolMode == ToolMode.Screwdriver){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.screwdriver));
        }
    }

    @Override
    @Optional.Method(modid = "techreborn")
    public boolean canHandleTool(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        return toolMode == ToolMode.Wrench;
    }

    @Override
    @Optional.Method(modid = "techreborn")
    public boolean handleTool(ItemStack stack, BlockPos blockPos, World world, EntityPlayer player, EnumFacing enumFacing, boolean b) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (ElectricItem.manager.getCharge(stack) >= 100 && toolMode == ToolMode.Wrench){
            this.damageItem(stack, 1, player);
            return true;
        }
        return false;
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(wrench);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        return toolMode == ToolMode.Wrench && ElectricItem.manager.getCharge(wrench) >= 100;
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
        this.damageItem(wrench, 1, player);
    }

    @Override
    @Optional.Method(modid = "projectred-core")
    public boolean canUse(EntityPlayer player, ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        return toolMode == ToolMode.Screwdriver && ElectricItem.manager.getCharge(stack) >= 100;
    }

    @Override
    @Optional.Method(modid = "projectred-core")
    public void damageScrewdriver(EntityPlayer player, ItemStack stack) {
        this.damageItem(stack, 1, player);
    }

    public enum ToolMode {
        Wrench,
        Hoe,
        Treetap,
        Screwdriver;

        private ToolMode() {
        }

        public ToolMode getNext() {
            if (this == Wrench) {
                return Hoe;
            } else if (this == Hoe) {
                return Treetap;
            } else if(this == Treetap) {
                if (Loader.isModLoaded("projectred-core")){
                    return Screwdriver;
                } else {
                    return Wrench;
                }
            } else {
                return Wrench;
            }
        }
    }
}
