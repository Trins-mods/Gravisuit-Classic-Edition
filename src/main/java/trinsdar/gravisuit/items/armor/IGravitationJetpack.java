package trinsdar.gravisuit.items.armor;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.wearable.armor.electric.QuantumSuit;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.util.GravisuitKeys;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;

import java.util.Map;
import java.util.WeakHashMap;

public interface IGravitationJetpack extends ILangHelper, IHasOverlay {

    AbilitySource SOURCE = Pal.getAbilitySource(GravisuitClassic.MODID, "gravisuit");
    default boolean armorTick(ItemStack stack, Level world, Player player){
        CompoundTag tag = this.nbtData(stack, true);
        boolean enabled = tag.getBoolean("engine_on");
        byte jetpackTicker = tag.getByte("JetpackTicker");
        PlayerHandler handler = PlayerHandler.getHandler(player);
        Entity entity = player.getRootVehicle();
        boolean server = IC2.PLATFORM.isSimulating();
        if (((IGravisuitPlayerHandler)handler).isFlightKeyDown() && !handler.screenOpen && jetpackTicker <= 0) {
            tag.putByte("JetpackTicker", (byte)10);
            boolean disabled = !enabled;
            tag.putBoolean("engine_on", disabled);
            if (disabled) {
                tag.putByte("LastUseMode", (byte) IC2JetpackBase.JetpackUseMode.NONE.getIndex());
            }
            if (server) {
                player.displayClientMessage(this.translate("message.gravi_engine_" + (disabled ? "on" : "off")), false);
            }
            if (server && enabled && !player.isCreative() && !player.isSpectator()){
                SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
            }
            enabled = disabled;
        }
        if (enabled) {
            if (ElectricItem.MANAGER.getCharge(stack) >= 512){
                if (jetpackTicker > 0) {
                    --jetpackTicker;
                    tag.putByte("JetpackTicker", jetpackTicker);
                }
                if (!player.isCreative() && !player.isSpectator()){
                    /*if (handler.boostKeyDown && player.isSprinting() && hasQuantumLegs(player)){
                        this.useEu(player, stack, 1024);
                    }else {
                        this.useEu(player, stack, 512);
                    }*/
                    this.useEu(player, stack, player.getAbilities().flying ? 512 : 256);
                    if (ElectricItem.MANAGER.getCharge(stack) < 512){
                        tag.putBoolean("ResetFlying", true);
                    }
                }
                if (server) SOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
                //player.maxUpStep = 1.0625F;
                boolean flying = player.getAbilities().flying;
                Attribute flyingSpeed = Attributes.FLYING_SPEED;
                if (flying && hasQuantumLegs(player)){
                    //AttributeModifier currentModifier = player.getAttribute(flyingSpeed).getModifier();
                }
                /*if(flying){
                    boolean sneaking = player.isCrouching();

                    float speed = 0.08f
                            * (flying ? 0.6f : 1.0f)
                            * (sneaking ? 0.1f : 1.0f);

                    if (player.moveForward > 0f) {
                        player.moveRelative(0f, 0f, 1f, speed);
                    } else if (player.moveForward < 0f) {
                        player.moveRelative(0f, 0f, 1f, -speed * 0.3f);
                    }

                    if (player.moveStrafing != 0f) {
                        player.moveRelative(1f, 0f, 0f, speed * 0.5f * Math.signum(player.moveStrafing));
                    }
                }*/
                return false;
            }else {
                if (tag.contains("ResetFlying")) {
                    tag.remove("ResetFlying");
                    tag.putBoolean("engine_on", false);
                    if (server && !player.isCreative() && !player.isSpectator()){
                        player.displayClientMessage(this.translate("message.gravi_engine_off"), false);
                        player.displayClientMessage(this.translate("message.gravi_engine_low_power").withStyle(ChatFormatting.RED), false);
                        SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
                    }
                }
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    default void toolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(buildKeyDescription(GravisuitKeys.G_KEY, GravisuitLang.graviEngineToggle));
        boolean enabled = isJetpackEnabled(stack);
        String lang = enabled ? GravisuitLang.graviEngineOnInfo : GravisuitLang.graviEngineOffInfo;
        helper.addSimpleToolTip(lang);
    }

    @OnlyIn(Dist.CLIENT)
    default MutableComponent buildKeyDescription(KeyMapping key, String translationKey, Object... args) {
        return this.buildKeyDescription(combineKeys(key).withStyle(ChatFormatting.GOLD), this.translate(translationKey, args).withStyle(ChatFormatting.UNDERLINE));
    }

    CompoundTag nbtData(ItemStack stack, boolean create);

    default boolean isJetpackEnabled(ItemStack stack){
        CompoundTag tag = this.nbtData(stack, false);
        if (tag == null) return false;
        return tag.getBoolean("engine_on");
    }

    default void useEu(Player player, ItemStack stack, int amount){
        if (stack.getItem() != this) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        } else if (!ElectricItem.MANAGER.use(stack, amount, player)) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        }
    }

    Map<Player, Boolean> playersWithFlight = new WeakHashMap<>();



    default void onLivingTickEvent(LivingEvent.LivingTickEvent event){
        if (event.getEntity() instanceof Player player && !player.level.isClientSide) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            boolean flight = isStackGravisuit(stack) && isJetpackEnabled(stack);
            if (!playersWithFlight.containsKey(player)) {
                playersWithFlight.put(player, false);
            }
            boolean previousHasFlight = playersWithFlight.get(player);
            if (!previousHasFlight && flight){
                playersWithFlight.put(player, true);
            }
            if (previousHasFlight && !flight) {
                playersWithFlight.put(player, false);
                if (!player.isCreative() && !player.isSpectator()) {
                    SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
                }
            }
        }
    }

    private boolean isStackGravisuit(ItemStack stack){
        return stack.getItem() instanceof IGravitationJetpack || (stack.getItem() instanceof IC2ModularElectricArmor armor && armor.getInstalledModules(stack).stream().anyMatch(s -> s.getItem() instanceof IGravitationJetpack));
    }

    public static boolean hasQuantumLegs(Player player){
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);

        return !legs.isEmpty() && legs.getItem() instanceof QuantumSuit;
    }
}
