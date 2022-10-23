package trinsdar.gravisuit.items.armor;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.wearable.armor.electric.QuantumSuit;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ILangHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;

import java.util.Map;
import java.util.WeakHashMap;

public interface IGravitationJetpack extends ILangHelper {
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
            if (server) {
                String lang = disabled ? GravisuitLang.graviEngineOn : GravisuitLang.graviEngineOff;
                player.displayClientMessage(this.translate(lang), false);
            }
        }
        if (enabled) {
            if (ElectricItem.MANAGER.getCharge(stack) >= 1024){
                if (jetpackTicker > 0) {
                    --jetpackTicker;
                    tag.putByte("JetpackTicker", jetpackTicker);
                }
                if (!player.isCreative() && !player.isSpectator()){
                    if (handler.boostKeyDown && player.isSprinting() && hasQuantumLegs(player)){
                        this.useEu(player, stack, 1024);
                    }else {
                        this.useEu(player, stack, 512);
                    }
                }
                player.getAbilities().mayfly = true;
                player.maxUpStep = 1.0625F;
                boolean flying = player.getAbilities().flying;
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
                if (!player.isCreative() && !player.isSpectator()){
                    player.maxUpStep = 0.6F;
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                }
            }
        } else {
            if (!player.isCreative() && !player.isSpectator() && player.getAbilities().flying){
                player.maxUpStep = 0.6F;
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
        }
        return true;
    }

    CompoundTag nbtData(ItemStack stack, boolean create);

    default void useEu(Player player, ItemStack stack, int amount){
        if (stack.getItem() != this) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        } else if (!ElectricItem.MANAGER.use(stack, amount, player)) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        }
    }

    Map<Player, ItemStack> previousItems = new WeakHashMap<>();



    default void onLivingTickEvent(LivingEvent.LivingTickEvent event){
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (previousItems.containsKey(player)){
                ItemStack previous = previousItems.get(player);
                if (isStackGravisuit(previous) && !previous.equals(stack)){
                    player.maxUpStep = 0.6F;
                    if (!player.isCreative() && !player.isSpectator()) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                    }

                }
                previousItems.put(player, stack);
            } else {

                previousItems.put(player, stack);
            }
            /*String key = playerKey(player);
            ItemStack stack = new ItemStack(Registry.gravisuit);
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            boolean disabled = nbt.getBoolean("disabled");

            Boolean hasSet = ItemArmorGravisuit.hasGravisuit(player);
            if (playersWithSet.contains(key)) {
                if (!hasSet) {
                    player.stepHeight = 0.6F;
                    if (!player.capabilities.isCreativeMode && !player.isSpectator()) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                    }
                    playersWithSet.remove(key);
                }
            } else if (hasSet) {
                playersWithSet.add(key);
            }*/
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
