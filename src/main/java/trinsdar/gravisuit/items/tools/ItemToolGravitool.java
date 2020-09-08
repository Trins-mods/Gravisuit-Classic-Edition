package trinsdar.gravisuit.items.tools;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import ic2.api.classic.audio.PositionSpec;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.tool.electric.ItemElectricToolPrecisionWrench;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IAdvancedTexturedItem;
import ic2.core.util.misc.StackUtil;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.api.ICustomToolHandler;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.compat.ic2c_extras.Ic2cExtrasCodeHelper;
import trinsdar.gravisuit.util.RotationHelper;

import java.util.Arrays;
import java.util.List;

@Optional.InterfaceList({
        @Optional.Interface(iface = "reborncore.api.ICustomToolHandler", modid = "techreborn", striprefs = true),
        @Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore", striprefs = true),
        @Optional.Interface(iface = "mrtjp.projectred.api.IScrewdriver", modid = "projectred-core", striprefs = true),
        @Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "cofhcore", striprefs = true)
})
public class ItemToolGravitool extends ItemElectricToolPrecisionWrench implements ICustomToolHandler, IToolWrench, IScrewdriver, IAdvancedTexturedItem, IToolHammer {

    private int maxCharge;
    private int transferLimit;
    private int tier;

    public ModelResourceLocation[] model = new ModelResourceLocation[4];

    public ItemToolGravitool() {
        super();
        this.maxCharge = GravisuitConfig.powerValues.gravitoolStorage;
        this.transferLimit = GravisuitConfig.powerValues.gravitoolTransfer;
        this.tier = 2;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setRegistryName("gravitool");
        this.setUnlocalizedName(GravisuitLang.gravitool);
        this.setCreativeTab(IC2.tabIC2);
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public void setMaxCharge(int storage){
        this.maxCharge = storage;
    }

    public void setMaxTransfer(int maxTransfer) {
        this.transferLimit = maxTransfer;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return (double) maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return (double) transferLimit;
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0, 1, 2, 3);
    }

    @Override
    public boolean canOverrideLossChance(ItemStack stack) {
        return true;
    }
        
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
	    return true;
    }
        
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
	    IC2.audioManager.playOnce(player, PositionSpec.Hand, GravisuitSounds.toolGraviToolSound, true, IC2.audioManager.getDefaultVolume());
            if (this.getDamage(stack) == 3) {
                this.setDamage(stack, 0);
                IC2.platform.messagePlayer(player, TextFormatting.AQUA, GravisuitLang.messageWrench);
            } else if (this.getDamage(stack) == 0){
                this.setDamage(stack, 1);
                IC2.platform.messagePlayer(player, TextFormatting.GOLD, GravisuitLang.messageHoe);
            } else if (this.getDamage(stack) == 1){
                this.setDamage(stack, 2);
                IC2.platform.messagePlayer(player, TextFormatting.DARK_GREEN, GravisuitLang.messageTreetap);
            }else {
                this.setDamage(stack, 3);
                IC2.platform.messagePlayer(player, TextFormatting.LIGHT_PURPLE, GravisuitLang.messageScrewdriver);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.getDamage(stack) == 0){
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }else if (this.getDamage(stack) == 3){
            return RotationHelper.rotateBlock(world, pos, side) ? EnumActionResult.SUCCESS: EnumActionResult.PASS;
        }else {
            return EnumActionResult.PASS;
        }

    }

    @Override
    public boolean hasBigCost(ItemStack stack) {
        return false;
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
        if (this.getDamage(stack) == 1){
            return Ic2Items.electricHoe.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else if (this.getDamage(stack) == 2){
            return Ic2Items.electricTreeTap.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (this.getDamage(stack) == 0){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.wrench));
        }else if (this.getDamage(stack) == 1){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.hoe));
        }else if (this.getDamage(stack) == 2){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.treetap));
        }else if (this.getDamage(stack) == 3){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.screwdriver));
        }
        if (GravisuitConfig.enableGravitoolRequiresLosslessPrecisionWrench && !(Loader.isModLoaded("ic2c_extras") && Ic2cExtrasCodeHelper.isOverridingLossy())){
            tooltip.add(TextFormatting.GREEN + GravisuitLang.craftingGravitool.getLocalized());
        }
        if (this.isImport(stack)) {
            tooltip.add(Ic2InfoLang.treeTapEffect.getLocalized());
        }
    }

    public boolean isImport(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("Import");
    }

    @Override
    @Optional.Method(modid = "techreborn")
    public boolean canHandleTool(ItemStack stack) {
        return this.getDamage(stack) == 0 && ElectricItem.manager.getCharge(stack) >= 100;
    }

    @Override
    @Optional.Method(modid = "techreborn")
    public boolean handleTool(ItemStack stack, BlockPos blockPos, World world, EntityPlayer player, EnumFacing enumFacing, boolean b) {
        return this.getDamage(stack) == 0 && ElectricItem.manager.use(stack, 100, player);
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
        return this.getDamage(player.getHeldItem(hand)) == 0 && ElectricItem.manager.getCharge(wrench) >= 100;
    }

    @Override
    @Optional.Method(modid = "buildcraftcore")
    public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
        ElectricItem.manager.use(wrench, 100, player);
    }

    @Override
    @Optional.Method(modid = "projectred-core")
    public boolean canUse(EntityPlayer player, ItemStack stack) {
        return this.getDamage(stack) == 3 && ElectricItem.manager.getCharge(stack) >= 100;
    }

    @Override
    @Optional.Method(modid = "projectred-core")
    public void damageScrewdriver(EntityPlayer player, ItemStack stack) {
        this.damageItem(stack, 1, player);
    }

    @Optional.Method(modid = "cofhcore")
    @Override
    public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos) {
        return this.getDamage(item) == 0 && ElectricItem.manager.getCharge(item) >= 100;
    }

    @Optional.Method(modid = "cofhcore")
    @Override
    public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity) {
        return this.getDamage(item) == 0 && ElectricItem.manager.getCharge(item) >= 100;
    }

    @Optional.Method(modid = "cofhcore")
    @Override
    public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos) {
        ElectricItem.manager.use(item, 100, user);
    }

    @Optional.Method(modid = "cofhcore")
    @Override
    public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity) {
        ElectricItem.manager.use(item, 100, user);
    }
}
