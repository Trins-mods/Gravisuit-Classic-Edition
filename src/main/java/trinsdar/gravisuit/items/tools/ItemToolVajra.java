package trinsdar.gravisuit.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.tool.electric.DrillTool;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;

import java.util.Map;

public class ItemToolVajra extends DrillTool {

    public ItemToolVajra() {
        super("vajra", 4, 3333, 16384.0F);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "vajra"), this);
        this.registerTools(Tool.AXE.withLevel(4), Tool.HOE.withLevel(4));
        this.capacity = 3000000;
        this.transferLimit = 1000;
        this.tier = 3;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            boolean silkTouch = nbt.getBoolean("silkTouch");
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            if (IC2.PLATFORM.isSimulating()) {
                if (silkTouch){
                    nbt.putBoolean("silkTouch", false);
                    enchantments.remove(Enchantments.SILK_TOUCH, 1);
                    EnchantmentHelper.setEnchantments(enchantments, stack);
                    playerIn.displayClientMessage(this.translate(GravisuitLang.silkTouchOff), false);
                }else {
                    nbt.putBoolean("silkTouch", true);
                    enchantments.put(Enchantments.SILK_TOUCH, 1);
                    EnchantmentHelper.setEnchantments(enchantments, stack);
                    playerIn.displayClientMessage(this.translate(GravisuitLang.silkTouchOn), false);
                }
                return InteractionResultHolder.success(stack);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    private boolean shouldBreak(Player playerIn, Level worldIn, BlockPos pos) {
        BlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getMaterial() == Material.AIR) {
            return false;
        }
        if (blockState.getMaterial().isLiquid()) {
            return false;
        }
        float blockHardness = blockState.getDestroySpeed(worldIn, pos);
        if (blockHardness < 0) {
            return false;
        }
        if (!blockState.getBlock().canHarvestBlock(blockState, worldIn, pos, playerIn)){
            return false;
        }

        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        SoundType soundType = block.getSoundType(blockState, world, pos, player);
        if (ElectricItem.MANAGER.getCharge(stack) >= getEnergyCost(stack) && shouldBreak(player, world, pos) && IC2.PLATFORM.isSimulating()){
            ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack), player);
            world.destroyBlock(pos, true, player);
            //blockState.getBlock().harvestBlock(world, player, pos, blockState, world.getTileEntity(pos), stack);
            //world.playSound(null, pos, soundType.getBreakSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
            //world.setBlockToAir(pos);
            //world.removeTileEntity(pos);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            if (ElectricItem.MANAGER.getCharge(stack) >= getEnergyCost(stack) * 2){
                multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Vajra Powered Damage", 25, AttributeModifier.Operation.ADDITION));
            }else {
                multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Vajra Unpowered Damage", 3, AttributeModifier.Operation.ADDITION));
            }
        }
        return multimap;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("vajra");
    }

    /*public void setTier(int tier){
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
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        boolean silkTouch = nbt.getBoolean("silkTouch");
        if (silkTouch){
            tooltip.add(GravisuitLang.silkMode.getLocalizedFormatted(GravisuitLang.vajraSilktouchOn));
        }else{
            tooltip.add(GravisuitLang.silkMode.getLocalizedFormatted(GravisuitLang.vajraSilktouchOff));
        }
        List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
        ctrlTip.add(Ic2Lang.onItemRightClick.getLocalized());
        ctrlTip.add(Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), GravisuitLang.vajraSilktouchToggle.getLocalized()));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        SoundType soundType = block.getSoundType(blockState, world, pos, player);
        if (ElectricItem.manager.getCharge(stack) >= getEnergyCost(stack) && shouldBreak(player, world, pos)){
            ElectricItem.manager.use(stack, this.getEnergyCost(stack), player);
            blockState.getBlock().harvestBlock(world, player, pos, blockState, world.getTileEntity(pos), stack);
            world.playSound(null, pos, soundType.getBreakSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
            world.setBlockToAir(pos);
            world.removeTileEntity(pos);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            IC2.achievements.issueStat((EntityPlayer) entityLiving, "blocksDrilled");
        }
        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[13];
    }

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.DIGGER;
    }

    @Override
    public boolean isBasicDrill(ItemStack d) {
        return !d.isItemEnchantable();
    }

    @Override
    public int getExtraSpeed(ItemStack d) {
        int pointBoost = this.getPointBoost(d);
        return 0 + pointBoost;
    }

    private int getPointBoost(ItemStack drill) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, drill);
        return lvl <= 0 ? 0 : lvl * lvl + 1;
    }

    @Override
    public int getExtraEnergyCost(ItemStack d) {
        int points = this.getEnergyChange(d);
        return points > 0 ? points : 0;
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
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        ElectricItem.manager.use(stack, (double)getEnergyCost(stack) * 2, attacker);
        return true;
    }

    @Override
    public boolean canMine(ItemStack d) {
        return ElectricItem.manager.canUse(d, this.getEnergyCost(d));
    }

    @Override
    public boolean canMineBlock(ItemStack d, IBlockState state, IBlockAccess access, BlockPos pos) {
        return ForgeHooks.canToolHarvestBlock(access, pos, d);
    }*/
}
