package trinsdar.gravisuit.items.tools;


import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.tool.electric.ElectricWrenchTool;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.proxy.ClientProxy;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.RotationHelper;

import java.util.List;

public class ItemToolGravitool extends ElectricWrenchTool {

    //public ModelResourceLocation[] model = new ModelResourceLocation[4];

    public ItemToolGravitool() {
        super("gravitool", null);
        this.capacity = GravisuitConfig.powerValues.gravitoolStorage;
        this.transferLimit = GravisuitConfig.powerValues.gravitoolTransfer;
        this.tier = 2;
        this.losslessUses = -1;
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"gravitool"), this);
        if (IC2.PLATFORM.isRendering()){
            ClientProxy.registerBatteryPropertyOverrides(this);
        }
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public void setMaxCharge(int storage){
        this.capacity = storage;
    }

    public void setMaxTransfer(int maxTransfer) {
        this.transferLimit = maxTransfer;
    }

    @Override
    public boolean canOverrideLoss(ItemStack stack) {
        return true;
    }
        
    //@Override
    /*public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
	    return true;
    }*/
    /*public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        if (toolClass.equals("wrench") && this.getDamage(stack) > 0){
            return -1;
        }
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }*/

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            byte mode = nbt.getByte("mode");
            if (mode == 3) {
                nbt.putByte("mode", (byte) 0);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageWrench, ChatFormatting.AQUA), false);
            } else if (mode == 0){
                nbt.putByte("mode", (byte) 1);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageHoe, ChatFormatting.GOLD), false);
            } else if (mode == 1){
                nbt.putByte("mode", (byte) 2);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageTreetap, ChatFormatting.DARK_GREEN), false);
            }else {
                nbt.putByte("mode", (byte) 3);
                playerIn.displayClientMessage(this.translate(GravisuitLang.messageScrewdriver, ChatFormatting.LIGHT_PURPLE), false);
            }
            return InteractionResultHolder.success(stack);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    public byte getMode(ItemStack stack){
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getByte("mode");
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(context.getPlayer())){

        }
        return super.onItemUseFirst(stack, context);
    }

    /*@Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.getDamage(stack) == 0){
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }else if (this.getDamage(stack) == 3){
            return RotationHelper.rotateBlock(world, pos, side) ? EnumActionResult.SUCCESS: EnumActionResult.PASS;
        }else {
            return EnumActionResult.PASS;
        }

    }*/

    @Override
    public boolean hasBigCost(ItemStack stack) {
        return false;
    }


    /*@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (this.getDamage(stack) == 1){
            return Ic2Items.electricHoe.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else if (this.getDamage(stack) == 2){
            return Ic2Items.electricTreeTap.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }else {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
    }*/

    /*@Override
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
    }*/

    public boolean isImport(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("inv_import");
    }

    /*@Override
    public boolean isChangingCropDrops(ItemStack itemStack) {
        return this.getDamage(itemStack) == 1;
    }*/
}
