package trinsdar.gravisuit.items.tools;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.platform.registry.Ic2Sounds;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.util.Config;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemToolAdvancedChainsaw extends ItemElectricTool implements IStaticTexturedItem {
    public static final ItemStack ironAxe;

    public ItemToolAdvancedChainsaw() {
        super(0.0F, 0.0F, ToolMaterial.IRON);
        this.attackDamage = 4.0F;
        this.maxCharge = Config.advancedChainsawStorage;
        this.transferLimit = Config.advancedChainsawTransfer;
        this.operationEnergyCost = 100;
        this.tier = 2;
        this.efficiency = 15.0F;
        this.setHarvestLevel("axe", 2);
        this.setRegistryName("advancedchainsaw");
        this.setUnlocalizedName(GravisuitLang.advancedChainsaw);
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
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        if (!tag.getBoolean("noShear")) {
            tooltip.add(GravisuitLang.messageAdvancedChainsawNormal.getLocalized());
        } else if (tag.getBoolean("noShear")) {
            tooltip.add(GravisuitLang.messageAdvancedChainsawNoShear.getLocalized());
        }
        if (!tag.getBoolean("noTreeCutting")){
            tooltip.add(GravisuitLang.messageAdvancedChainsawTreeCuttingOn.getLocalized());
        } else {
            tooltip.add(GravisuitLang.messageAdvancedChainsawTreeCuttingOff.getLocalized());
        }
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), GravisuitLang.advancedChainsawShearToggle.getLocalized()));
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(GravisuitLang.gravisuitToggleCombo.getLocalizedFormatted(IC2.keyboard.getKeyName(9), IC2.keyboard.getKeyName(2)), GravisuitLang.advancedChainsawTreeCuttingToggle.getLocalized()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[8];
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return ironAxe.canHarvestBlock(state) || state.getBlock() == Blocks.WEB;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        if (!ElectricItem.manager.canUse(stack, this.getEnergyCost(stack))) {
            return 1.0F;
        } else {
            return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
                    && material != Material.LEAVES && material != Material.CACTUS && material != Material.GOURD && material != Material.CLOTH ? super.getDestroySpeed(stack, state) : this.efficiency;
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase entity, EnumHand hand) {
    	NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
    	if (!tag.getBoolean("noShear")) {
            return Ic2Items.chainSaw.getItem().itemInteractionForEntity(stack, playerIn, entity, hand);
    	} 
    	return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
    	NBTTagCompound tag = StackUtil.getOrCreateNbtData(itemstack);
        World worldIn = player.world;
        if (!player.isSneaking() && !tag.getBoolean("noTreeCutting")) {
            Set<BlockPos> positions = getTargetBlocks(worldIn, pos, player);
            if (isTree(pos, player) && !positions.isEmpty()){
                for (BlockPos pos2 : positions) {
                    breakBlock(pos2, itemstack, worldIn, pos, player);
                }
            }
        }
        if (!tag.getBoolean("noShear")) {
        	return Ic2Items.chainSaw.getItem().onBlockStartBreak(itemstack, pos, player);
        } else {
        	return false; 
        }
    }

    public Set<BlockPos> getTargetBlocks(World worldIn, BlockPos pos, @Nullable EntityPlayer playerIn) {
        Set<BlockPos> targetBlocks = new HashSet<BlockPos>();
        if (playerIn == null) {
            return new HashSet<BlockPos>();
        }
        RayTraceResult raytrace = rayTrace(worldIn, playerIn, false);
        if(raytrace == null || raytrace.sideHit == null){
            return Collections.emptySet();
        }
        EnumFacing enumfacing = raytrace.sideHit;
        if (enumfacing == EnumFacing.DOWN){
            return Collections.emptySet();
        }
        BlockPos left = left(enumfacing, pos);
        BlockPos leftback = back(enumfacing, left);
        BlockPos back = back(enumfacing, pos);
        BlockPos right = right(enumfacing, pos);
        BlockPos rightback = back(enumfacing, right);
        if (enumfacing != EnumFacing.UP && worldIn.getBlockState(back).getBlock().isWood(worldIn, back)){
            targetBlocks.add(back);
            for (int i = 1; i < 60; i++) {
                BlockPos nextPos = back.up(i);
                IBlockState nextState = worldIn.getBlockState(nextPos);
                if (nextState.getBlock().isWood(worldIn, nextPos)) {
                    targetBlocks.add(nextPos);
                } else {
                    break;
                }
            }
            if (worldIn.getBlockState(left).getBlock().isWood(worldIn, left) && worldIn.getBlockState(leftback).getBlock().isWood(worldIn, leftback)){
                targetBlocks.add(left);
                targetBlocks.add(leftback);
                for (int i = 1; i < 60; i++) {
                    BlockPos nextPos = left.up(i);
                    IBlockState nextState = worldIn.getBlockState(nextPos);
                    if (nextState.getBlock().isWood(worldIn, nextPos)) {
                        targetBlocks.add(nextPos);
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < 60; i++) {
                    BlockPos nextPos = leftback.up(i);
                    IBlockState nextState = worldIn.getBlockState(nextPos);
                    if (nextState.getBlock().isWood(worldIn, nextPos)) {
                        targetBlocks.add(nextPos);
                    } else {
                        break;
                    }
                }
            } else if (worldIn.getBlockState(right).getBlock().isWood(worldIn, right) && worldIn.getBlockState(rightback).getBlock().isWood(worldIn, rightback)){
                targetBlocks.add(right);
                targetBlocks.add(rightback);
                for (int i = 1; i < 60; i++) {
                    BlockPos nextPos = right.up(i);
                    IBlockState nextState = worldIn.getBlockState(nextPos);
                    if (nextState.getBlock().isWood(worldIn, nextPos)) {
                        targetBlocks.add(nextPos);
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < 60; i++) {
                    BlockPos nextPos = rightback.up(i);
                    IBlockState nextState = worldIn.getBlockState(nextPos);
                    if (nextState.getBlock().isWood(worldIn, nextPos)) {
                        targetBlocks.add(nextPos);
                    } else {
                        break;
                    }
                }
            }
        }
        
        if (worldIn.getBlockState(pos).getBlock().isWood(worldIn, pos)){
            for (int i = 1; i < 60; i++) {
                BlockPos nextPos = pos.up(i);
                IBlockState nextState = worldIn.getBlockState(nextPos);
                if (nextState.getBlock().isWood(worldIn, nextPos)) {
                    targetBlocks.add(nextPos);
                } else {
                    break;
                }
            }
        }
        return targetBlocks;
    }

    public BlockPos left(EnumFacing facing, BlockPos old){
        if (facing == EnumFacing.NORTH){
            return old.add( 1, 0, 0);
        }
        if (facing == EnumFacing.WEST){
            return old.add( 0, 0, -1);
        }
        if (facing == EnumFacing.SOUTH){
            return old.add( -1, 0, 0);
        }
        if (facing == EnumFacing.EAST){
            return old.add( 0, 0, 1);
        }
        return old;
    }

    public BlockPos right(EnumFacing facing, BlockPos old){
        if (facing == EnumFacing.NORTH){
            return old.add( -1, 0, 0);
        }
        if (facing == EnumFacing.WEST){
            return old.add( 0, 0, 1);
        }
        if (facing == EnumFacing.SOUTH){
            return old.add( 1, 0, 0);
        }
        if (facing == EnumFacing.EAST){
            return old.add( 0, 0, -1);
        }
        return old;
    }

    public BlockPos back(EnumFacing facing, BlockPos old){
        if (facing == EnumFacing.NORTH){
            return old.add( 0, 0, 1);
        }
        if (facing == EnumFacing.WEST){
            return old.add( 1, 0, 0);
        }
        if (facing == EnumFacing.SOUTH){
            return old.add( 0, 0, -1);
        }
        if (facing == EnumFacing.EAST){
            return old.add( -1, 0, 0);
        }
        return old;
    }

    private boolean shouldBreak(EntityPlayer playerIn, World worldIn, BlockPos originalPos, BlockPos pos) {
        if (originalPos.equals(pos)) {
            return false;
        }
        IBlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getMaterial() == Material.AIR) {
            return false;
        }
        if (blockState.getMaterial().isLiquid()) {
            return false;
        }
        float blockHardness = blockState.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
        if (blockHardness == -1.0F) {
            return false;
        }
        float originalHardness = worldIn.getBlockState(originalPos).getPlayerRelativeBlockHardness(playerIn, worldIn, originalPos);
        if ((originalHardness / blockHardness) > 10.0F) {
            return false;
        }

        return true;
    }

    public boolean isTree(BlockPos pos, EntityPlayer player){
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            IC2.achievements.issueStat((EntityPlayer) entityLiving, "blocksSawed");
        }
        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    public void breakBlock(BlockPos pos, ItemStack saw, World world, BlockPos oldPos, EntityPlayer player) {
        if (oldPos == pos) {
            return;
        }
        if (!ElectricItem.manager.canUse(saw, this.getEnergyCost(saw))) {
            return;
        }
        IBlockState blockState = world.getBlockState(pos);
        if (blockState.getBlockHardness(world, pos) == -1.0F) {
            return;
        }
        ElectricItem.manager.use(saw, this.getEnergyCost(saw), player);
        blockState.getBlock().harvestBlock(world, player, pos, blockState, world.getTileEntity(pos), saw);
        world.setBlockToAir(pos);
        world.removeTileEntity(pos);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (player.isSneaking()){
                if (tag.getBoolean("noTreeCutting")) {
                    tag.setBoolean("noTreeCutting", false);
                    IC2.platform.messagePlayer(player, TextFormatting.GREEN, GravisuitLang.messageAdvancedChainsawTreeCuttingOn);
                } else {
                    tag.setBoolean("noTreeCutting", true);
                    IC2.platform.messagePlayer(player, TextFormatting.RED, GravisuitLang.messageAdvancedChainsawTreeCuttingOff);
                }
            } else {
                if (tag.getBoolean("noShear")) {
                    tag.setBoolean("noShear", false);
                    IC2.platform.messagePlayer(player, TextFormatting.GREEN, GravisuitLang.messageAdvancedChainsawNormal);
                } else {
                    tag.setBoolean("noShear", true);
                    IC2.platform.messagePlayer(player, TextFormatting.RED, GravisuitLang.messageAdvancedChainsawNoShear);
                }
            }
        	return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(worldIn, player, handIn);	
        }
    }

    @Override
    public EnumEnchantmentType getType(ItemStack item) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public boolean isSpecialSupported(ItemStack item, Enchantment ench) {
        return ench instanceof EnchantmentDamage;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return Ic2Items.chainSaw.getItem().hitEntity(stack, target, attacker);
    }

    static {
        ironAxe = new ItemStack(Items.IRON_AXE);
    }
}
