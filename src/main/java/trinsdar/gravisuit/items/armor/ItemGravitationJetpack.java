package trinsdar.gravisuit.items.armor;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.wearable.armor.electric.QuantumSuit;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;
import trinsdar.gravisuit.util.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ItemGravitationJetpack extends IC2ElectricJetpackBase implements IGravitationJetpack {
    public ItemGravitationJetpack() {
        super("gravitation_jetpack", EquipmentSlot.CHEST, null);
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID,"gravitation_jetpack"), this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return 500000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 3;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 1000;
    }

    @Override
    public boolean canDoRocketMode(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isElectricJetpack(ItemStack itemStack) {
        return true;
    }

    @Override
    public float getPower(ItemStack itemStack) {
        return 1.4f;
    }

    @Override
    public float getThruster(ItemStack itemStack, HoverMode hoverMode) {
        return switch (hoverMode){
            case ADV -> 1.8f;
            case BASIC -> 1.2f;
            case NONE -> 0.6f;
        };
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return worldHeight;
    }

    @Override
    public int getMaxRocketCharge(ItemStack itemStack) {
        return 30000;
    }

    @Override
    public int getFuelCost(ItemStack itemStack, HoverMode hoverMode) {
        return switch (hoverMode){
            case NONE -> 25;
            case BASIC -> 30;
            case ADV -> 40;
        };
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (this.armorTick(stack, world, player)) {
            super.onArmorTick(stack, world, player);
        }
    }

    @SubscribeEvent
    public void updatePlayerAbilityStatus(LivingEvent.LivingTickEvent event) {
        onLivingTickEvent(event);
    }

    @Override
    public String getTextureFolder() {
        return "jetpack";
    }

    @Override
    public String getTextureName() {
        return "gravitation_jetpack";
    }

    @Override
    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_electric_jetpack";
    }

    public MutableComponent buildKeyDescription(KeyMapping key, String translationKey, Object... args) {
        return this.buildKeyDescription(combineKeys(key).withStyle(ChatFormatting.GOLD), this.translate(translationKey, args).withStyle(ChatFormatting.UNDERLINE));
    }

    @Override
    public CompoundTag nbtData(ItemStack stack, boolean create) {
        return getNBTData(stack, create);
    }
}
