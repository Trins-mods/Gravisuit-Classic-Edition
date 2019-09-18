package trinsdar.gravisuit.items.tools;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Sounds;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.util.Config;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.Arrays;
import java.util.List;

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
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        //tooltip.add(GravisuitLang.advancedChainsaw1.getLocalized());
        if (this.getDamage(stack) == 0) {
        	tooltip.add(GravisuitLang.messageAdvancedChainsaw1.getLocalized());
        } else if (this.getDamage(stack) == 1) {
        	tooltip.add(GravisuitLang.messageAdvancedChainsaw2.getLocalized());
        }
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
    	if (this.getDamage(stack) == 0) {
            return Ic2Items.chainSaw.getItem().itemInteractionForEntity(stack, playerIn, entity, hand);
    	} 
    	return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        World worldIn = player.world;
        if (!player.isSneaking()) {
            for (int i = 1; i < 60; i++) {
                BlockPos nextPos = pos.up(i);
                IBlockState nextState = worldIn.getBlockState(nextPos);
                if (nextState.getBlock().isWood(worldIn, nextPos)) {
                    breakBlock(nextPos, itemstack, worldIn, pos, player);
                } else {
                    break;
                }
            }
        }
        if (ElectricItem.manager.canUse(itemstack, this.operationEnergyCost)) {
            IC2.audioManager.playOnce(player, Ic2Sounds.chainsawUseOne);
        }
        if (this.getDamage(itemstack) == 0) {
        	return Ic2Items.chainSaw.getItem().onBlockStartBreak(itemstack, pos, player);
        } else return false; 
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (this.getDamage(stack) == 1) {
                this.setDamage(stack, 0);
                IC2.platform.messagePlayer(player, TextFormatting.GREEN, GravisuitLang.messageAdvancedChainsaw1);
            } else {
                this.setDamage(stack, 1);
                IC2.platform.messagePlayer(player, TextFormatting.RED, GravisuitLang.messageAdvancedChainsaw2);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
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
