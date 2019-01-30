package trinsdar.gravisuit.items.tools;

import buildcraft.api.tools.IToolWrench;
import ic2.api.classic.audio.PositionSpec;
import ic2.api.classic.tile.ISpecialWrenchable;
import ic2.api.item.ElectricItem;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.base.util.info.misc.IWrench;
import ic2.core.item.armor.base.ItemArmorJetpackBase;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.item.tool.electric.ItemElectricToolHoe;
import ic2.core.item.tool.electric.ItemElectricToolPrecisionWrench;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Sounds;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IAdvancedTexturedItem;
import ic2.core.platform.textures.obj.ITexturedItem;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.api.ICustomToolHandler;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Optional.Interface(iface = "reborncore.api.ICustomToolHandler", modid = "techreborn", striprefs = true)
@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore", striprefs = true)
@Optional.Interface(iface = "mrtjp.projectred.api.IScrewdriver", modid = "projectred-core", striprefs = true)
public class ItemToolGravitool extends ItemElectricToolPrecisionWrench implements ICustomToolHandler, IToolWrench, IScrewdriver, IAdvancedTexturedItem {

    public ModelResourceLocation[] model = new ModelResourceLocation[4];

    public ItemToolGravitool() {
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setRegistryName("gravitool");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".gravitool");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0, 1, 2, 3);
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
                this.setDamage(stack, 0);
                IC2.platform.messagePlayer(player, GravisuitLang.messageWrench);
            } else if (toolMode == ToolMode.Hoe){
                this.setDamage(stack, 1);
                IC2.platform.messagePlayer(player, GravisuitLang.messageHoe);
            } else if (toolMode == ToolMode.Treetap){
                this.setDamage(stack, 2);
                IC2.platform.messagePlayer(player, GravisuitLang.messageTreetap);
            }else {
                this.setDamage(stack, 3);
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
        if (toolMode == ToolMode.Wrench && this.getDamage(player.getHeldItem(hand)) == 0){
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }else {
            return EnumActionResult.PASS;
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int meta) {
        return Ic2Icons.getTextures("gravisuit_items")[9 + meta];
    }

    @Override
    public int getTextureEntry(int var1) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public ModelResourceLocation createResourceLocationForStack(ItemStack stack) {
        int damage = stack.getItemDamage();
        ResourceLocation location = this.getRegistryName();
        String name = stack.getUnlocalizedName();
        this.model[damage] = new ModelResourceLocation(
                location.getResourceDomain() + name.substring(name.indexOf(".") + 1) + damage, "inventory");
        return this.model[damage];
    }

    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getResourceLocationForStack(ItemStack stack) {
        int damage = stack.getItemDamage();
        return this.model[damage];
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(player.getHeldItem(hand));
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolMode")];
        if (toolMode == ToolMode.Hoe && this.getDamage(stack) == 1){
            return Ic2Items.electricHoe.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else if (toolMode == ToolMode.Treetap && this.getDamage(stack) == 2){
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
        return true;
    }

    @Override
    @Optional.Method(modid = "techreborn")
    public boolean handleTool(ItemStack stack, BlockPos blockPos, World world, EntityPlayer player, EnumFacing enumFacing, boolean b) {
        return true;
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
        return true;
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
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
                return Screwdriver;
            } else {
                return Wrench;
            }
        }
    }
}
