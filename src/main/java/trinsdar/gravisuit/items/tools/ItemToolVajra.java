package trinsdar.gravisuit.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.tool.electric.DrillTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;

import javax.annotation.Nullable;
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
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.VAJRA_STORAGE.get();
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.VAJRA_TRANSFER.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)){
            ItemStack stack = playerIn.getItemInHand(handIn);
            CompoundTag nbt = stack.getOrCreateTag();
            boolean silkTouch = nbt.getBoolean("silkTouch");
            if (IC2.PLATFORM.isSimulating()) {
                if (silkTouch){
                    nbt.putBoolean("silkTouch", false);
                    playerIn.displayClientMessage(this.translate(GravisuitLang.silkTouchOff), false);
                }else {
                    nbt.putBoolean("silkTouch", true);
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
        if (ElectricItem.MANAGER.getCharge(stack) >= getEnergyCost(stack) && shouldBreak(player, world, pos) && IC2.PLATFORM.isSimulating()){
            ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack), player);
            destroyBlock(world, pos, true, player, stack);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    public boolean destroyBlock(Level level, BlockPos pos, boolean dropBlock, @Nullable Entity entity, ItemStack item) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.isAir()) {
            return false;
        } else {
            FluidState fluidstate = level.getFluidState(pos);
            if (!(blockstate.getBlock() instanceof BaseFireBlock)) {
                level.levelEvent(2001, pos, Block.getId(blockstate));
            }

            CompoundTag tag = item.getTag();
            BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, blockstate, entity instanceof Player player ? player : null);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()){
                return false;
            }
            if (dropBlock) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
                if (tag != null && tag.getBoolean("silkTouch")){
                    enchantments.put(Enchantments.SILK_TOUCH, 1);
                    EnchantmentHelper.setEnchantments(enchantments, item);
                }
                Block.dropResources(blockstate, level, pos, blockentity, entity, item);
                if (tag != null && tag.getBoolean("silkTouch")){
                    enchantments.remove(Enchantments.SILK_TOUCH);
                    EnchantmentHelper.setEnchantments(enchantments, item);
                }
            }

            boolean flag = level.setBlock(pos, fluidstate.createLegacyBlock(), 3, 512);
            if (flag) {
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(entity, blockstate));
            }

            return flag;
        }
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
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.BLOCK_EFFICIENCY ? InteractionResult.FAIL : super.getEnchantmentCompatibility(stack, ench);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("vajra");
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ElectricItem.MANAGER.use(stack, getEnergyCost(stack) * 2, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = stack.getOrCreateTag();
        boolean silkTouch = nbt.getBoolean("silkTouch");
        if (silkTouch){
            helper.addSimpleToolTip(GravisuitLang.silkMode, this.translate(GravisuitLang.vajraSilktouchOn));
        }else{
            helper.addSimpleToolTip(GravisuitLang.silkMode, this.translate(GravisuitLang.vajraSilktouchOff));
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, GravisuitLang.vajraSilktouchToggle));
    }

    /*public void setTier(int tier){
        this.tier = tier;
    }

    public void setMaxCharge(int storage){
        this.maxCharge = storage;
    }

    public void setMaxTransfer(int maxTransfer) {
        this.transferLimit = maxTransfer;
    }*/
}
