package trinsdar.gravisuit.items.tools;

import com.google.common.collect.ImmutableSet;
import ic2.api.classic.item.IMiningDrill;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.base.ItemElectricTool;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.platform.registry.Ic2Sounds;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.minecraft.item.ItemBlock.setTileEntityNBT;

public class ItemToolAdvancedDiamondDrill extends ItemElectricTool implements IStaticTexturedItem, IMiningDrill {

    public ItemToolAdvancedDiamondDrill() {
        super(0.0F, -3.0F, ToolMaterial.DIAMOND);
        this.setRegistryName("advanceddrill");
        this.setUnlocalizedName(GravisuitLang.advancedDrill);
        this.attackDamage = 4.0F;
        this.maxCharge = GravisuitConfig.powerValues.advancedDrillStorage;
        this.transferLimit = GravisuitConfig.powerValues.advancedDrillTransfer;
        this.tier = 2;
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolModeDrill")];
            toolMode = toolMode.getNext();
            nbt.setByte("ToolModeDrill", (byte)toolMode.ordinal());
            if (toolMode == ToolMode.NORMAL) {
                IC2.platform.messagePlayer(player, TextFormatting.AQUA, GravisuitLang.messageAdvancedDrillNormal);
            } else if (toolMode == ToolMode.LOWPOWER){
                IC2.platform.messagePlayer(player, TextFormatting.GOLD, GravisuitLang.messageAdvancedDrillLowPower);
            } else if (toolMode == ToolMode.FINE){
                IC2.platform.messagePlayer(player, TextFormatting.DARK_GREEN, GravisuitLang.messageAdvancedDrillFine);
            } else if (toolMode == ToolMode.BIGHOLES){
                IC2.platform.messagePlayer(player,TextFormatting.LIGHT_PURPLE, GravisuitLang.messageAdvancedDrillBigHoles);
            } else if (toolMode == ToolMode.MEDIUMHOLES){
                IC2.platform.messagePlayer(player, TextFormatting.BLUE, GravisuitLang.messageAdvancedDrillMediumHoles);
            } else {
                IC2.platform.messagePlayer(player, TextFormatting.DARK_PURPLE, GravisuitLang.messageAdvancedDrillTunnelHoles);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(worldIn, player, handIn);
        }
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolModeDrill")];
        if (toolMode == ToolMode.NORMAL){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillNormal));
        }else if (toolMode == ToolMode.LOWPOWER){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillLowPower));
        }else if (toolMode == ToolMode.FINE){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillFine));
        }else if (toolMode == ToolMode.BIGHOLES){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillBigHoles));
        }else if (toolMode == ToolMode.MEDIUMHOLES){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillMediumHoles));
        }else if (toolMode == ToolMode.TUNNELHOLES){
            tooltip.add(GravisuitLang.toolMode.getLocalizedFormatted(GravisuitLang.advancedDrillTunnelHoles));
        }
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), GravisuitLang.multiModes.getLocalized()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[7];
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return Items.DIAMOND_PICKAXE.canHarvestBlock(state) || Items.DIAMOND_SHOVEL.canHarvestBlock(state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolModeDrill")];
        World worldIn = player.world;
        if (!player.isSneaking()) {
            if (toolMode ==ToolMode.BIGHOLES){
                for (BlockPos additionalPos : get3X3TargetBlocks(worldIn, pos, player)) {
                    breakBlock(additionalPos, worldIn, player, stack);
                }
            }else if (toolMode == ToolMode.MEDIUMHOLES){
                for (BlockPos additionalPos : get2X3TargetBlocks(worldIn, pos, player)) {
                    breakBlock(additionalPos, worldIn, player, stack);
                }
            } else if (toolMode == ToolMode.TUNNELHOLES){
                breakBlock(get1x2TargetBlock(worldIn, pos, player), worldIn , player, stack);
            }

        }
        return false;
    }

    public Set<BlockPos> get3X3TargetBlocks(World worldIn, BlockPos pos, @Nullable EntityPlayer playerIn) {
        Set<BlockPos> targetBlocks = new HashSet<BlockPos>();
        if (playerIn == null) {
            return new HashSet<BlockPos>();
        }
        RayTraceResult raytrace = rayTrace(worldIn, playerIn, false);
        if(raytrace == null || raytrace.sideHit == null){
            return Collections.emptySet();
        }
        EnumFacing enumfacing = raytrace.sideHit;
        if (enumfacing == EnumFacing.SOUTH || enumfacing == EnumFacing.NORTH) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(i, j, 0);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        } else if (enumfacing == EnumFacing.EAST || enumfacing == EnumFacing.WEST) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(0, j, i);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        } else if (enumfacing == EnumFacing.DOWN || enumfacing == EnumFacing.UP) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(j, 0, i);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        }
        return targetBlocks;
    }

    public Set<BlockPos> get2X3TargetBlocks(World worldIn, BlockPos pos, @Nullable EntityPlayer playerIn) {
        Set<BlockPos> targetBlocks = new HashSet<BlockPos>();
        if (playerIn == null) {
            return new HashSet<BlockPos>();
        }
        RayTraceResult raytrace = rayTrace(worldIn, playerIn, false);
        if(raytrace == null || raytrace.sideHit == null){
            return Collections.emptySet();
        }
        EnumFacing enumfacing = raytrace.sideHit;
        EnumFacing enumFacing2 = playerIn.getHorizontalFacing();
        if (enumfacing == EnumFacing.SOUTH) {
            for (int i = 0; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(i, j, 0);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        } else if (enumfacing == EnumFacing.NORTH) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(i, j, 0);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        } else if (enumfacing == EnumFacing.EAST) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(0, j, i);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        } else if (enumfacing == EnumFacing.WEST) {
            for (int i = 0; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    BlockPos newPos = pos.add(0, j, i);
                    if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                        targetBlocks.add(newPos);
                    }
                }
            }
        }  else if (enumfacing == EnumFacing.DOWN || enumfacing == EnumFacing.UP) {
            if (enumFacing2 == EnumFacing.SOUTH){
                for (int i = -1; i < 1; i++) {
                    for (int j = -1; j < 2; j++) {
                        BlockPos newPos = pos.add(i, 0, j);
                        if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                            targetBlocks.add(newPos);
                        }
                    }
                }
            } else if (enumFacing2 == EnumFacing.NORTH){
                for (int i = 0; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        BlockPos newPos = pos.add(i, 0, j);
                        if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                            targetBlocks.add(newPos);
                        }
                    }
                }
            } else if (enumFacing2 == EnumFacing.EAST){
                for (int i = 0; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        BlockPos newPos = pos.add(j, 0, i);
                        if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                            targetBlocks.add(newPos);
                        }
                    }
                }
            }else {
                for (int i = -1; i < 1; i++) {
                    for (int j = -1; j < 2; j++) {
                        BlockPos newPos = pos.add(j, 0, i);
                        if (shouldBreak(playerIn, worldIn, pos, newPos)) {
                            targetBlocks.add(newPos);
                        }
                    }
                }
            }
        }
        return targetBlocks;
    }

    public BlockPos get1x2TargetBlock(World worldIn, BlockPos pos, @Nullable EntityPlayer playerIn) {
        if (playerIn == null) {
            return pos.up();
        }
        RayTraceResult raytrace = rayTrace(worldIn, playerIn, false);
        if(raytrace == null || raytrace.sideHit == null){
            return pos.up();
        }
        EnumFacing enumfacing = raytrace.sideHit;
        EnumFacing enumFacing2 = playerIn.getHorizontalFacing();
        BlockPos newPos = pos.up();
        if (enumfacing == EnumFacing.UP || enumfacing == EnumFacing.DOWN){
            if (enumFacing2 == EnumFacing.EAST){
                newPos = pos.add(1, 0, 0);
            } else if (enumFacing2 == EnumFacing.WEST){
                newPos = pos.add(-1, 0, 0);
            } else if (enumFacing2 == EnumFacing.SOUTH){
                newPos = pos.add(0, 0, 1);
            }else {
                newPos = pos.add(0, 0, -1);
            }
        }
        return newPos;
    }

    public void breakBlock(BlockPos pos, World world, EntityPlayer player, ItemStack drill) {
        IBlockState blockState = world.getBlockState(pos);

        if (!ElectricItem.manager.canUse(drill, this.getEnergyCost(drill))) {
            return;
        }

        ElectricItem.manager.use(drill, this.getEnergyCost(drill), player);
        blockState.getBlock().harvestBlock(world, player, pos, blockState, world.getTileEntity(pos), drill);
        world.setBlockToAir(pos);
        world.removeTileEntity(pos);
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

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
        if (!toolClass.equals("pickaxe") && !toolClass.equals("shovel") && !toolClass.equals("scoop")) {
            return -1;
        }
        return 3;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return 160;
    }

    @Override
    public float getMiningSpeed(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        if (!nbt.hasKey("ToolModeDrill")){
            return 48.0F;
        }
        ToolMode toolMode = ToolMode.values()[nbt.getByte("ToolModeDrill")];
        if (toolMode == ToolMode.NORMAL){
            return 48.0F;
        }else if (toolMode == ToolMode.LOWPOWER){
            return 16.0F;
        }else if (toolMode == ToolMode.FINE || toolMode == ToolMode.MEDIUMHOLES){
            return 8.0F;
        }else if (toolMode == ToolMode.BIGHOLES){
            return 5.3F;
        }else {
            return 24.0F;
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("pickaxe", "shovel", "scoop");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            IC2.achievements.issueStat((EntityPlayer) entityLiving, "blocksDrilled");
        }
        if (ElectricItem.manager.canUse(stack,this.getEnergyCost(stack))){
            IC2.audioManager.playOnce(entityLiving, Ic2Sounds.drillHard);
        }

        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public boolean isBasicDrill(ItemStack d) {
        return false;
    }

    @Override
    public int getExtraSpeed(ItemStack d) {
        int pointBoost = this.getPointBoost(d);
        return 9 + pointBoost;
    }

    private int getPointBoost(ItemStack drill) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, drill);
        return lvl <= 0 ? 0 : lvl * lvl + 1;
    }

    @Override
    public int getExtraEnergyCost(ItemStack d) {
        int points = this.getEnergyChange(d);
        return Math.max(points, 0);
    }

    public int getEnergyChange(ItemStack drill) {
        int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, drill);
        int unb = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, drill);
        int points = eff * eff + 1;
        points -= unb * (unb + unb);
        return points;
    }

    @Override
    public void useDrill(ItemStack d) {
        ElectricItem.manager.use(d, this.getEnergyCost(d), (EntityLivingBase) null);
    }

    @Override
    public boolean canMine(ItemStack d) {
        return ElectricItem.manager.canUse(d, this.getEnergyCost(d));
    }

    @Override
    public boolean canMineBlock(ItemStack d, IBlockState state, IBlockAccess access, BlockPos pos) {
        return ForgeHooks.canToolHarvestBlock(access, pos, d);
    }

    static boolean gtcxLoaded = Loader.isModLoaded("gtc_expansion");
    final ItemStack torch = new ItemStack(Blocks.TORCH);
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!gtcxLoaded){
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = ItemStack.EMPTY;
        if (!block.isReplaceable(worldIn, pos)) {
            pos = pos.offset(facing);
        }
        for (ItemStack stack1 : player.inventory.mainInventory){
            if (stack1.getItem() == torch.getItem()){
                stack = stack1;
                break;
            }
        }
        if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack) && worldIn.mayPlace(Blocks.TORCH, pos, false, facing, player)) {
            IBlockState state1 = Blocks.TORCH.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);
            if (placeBlockAt(stack, player, worldIn, pos, facing, hitX, hitY, hitZ, state1)) {
                state1 = worldIn.getBlockState(pos);
                SoundType soundtype = state1.getBlock().getSoundType(state1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }
            return EnumActionResult.SUCCESS;
        } else {
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }

    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.TORCH) {
            setTileEntityNBT(world, player, pos, stack);
            Blocks.TORCH.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
            }
        }

        return true;
    }

    public enum ToolMode {
        NORMAL,
        LOWPOWER,
        FINE,
        BIGHOLES,
        MEDIUMHOLES,
        TUNNELHOLES;

        private ToolMode() {
        }

        public ToolMode getNext() {
            if (this == NORMAL) {
                return LOWPOWER;
            } else if (this == LOWPOWER) {
                return FINE;
            } else if (this == FINE) {
                if (GravisuitConfig.enableAdvancedDrill3x3Mode){
                    return BIGHOLES;
                } else if (GravisuitConfig.enableAdvancedDrill2x3Mode){
                    return MEDIUMHOLES;
                }else if (GravisuitConfig.enableAdvancedDrill1x2Mode){
                    return TUNNELHOLES;
                }else {
                    return NORMAL;
                }
            } else if (this == BIGHOLES ) {
                if (GravisuitConfig.enableAdvancedDrill2x3Mode){
                    return MEDIUMHOLES;
                }else if (GravisuitConfig.enableAdvancedDrill1x2Mode){
                    return TUNNELHOLES;
                }else {
                    return NORMAL;
                }
            } else if (this == MEDIUMHOLES){
                if (GravisuitConfig.enableAdvancedDrill1x2Mode){
                    return TUNNELHOLES;
                }else {
                    return NORMAL;
                }
            } else {
                return NORMAL;
            }
        }
    }
}
