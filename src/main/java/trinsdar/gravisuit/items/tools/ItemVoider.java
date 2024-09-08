package trinsdar.gravisuit.items.tools;

import com.google.common.base.CaseFormat;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.GravisuitClassic;
import trinsdar.gravisuit.items.container.ItemInventoryRelocator;
import trinsdar.gravisuit.items.container.ItemInventoryVoider;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.util.Registry;

import java.util.List;

public class ItemVoider extends IC2ElectricItem implements IItemModel, IHasHeldSlotInventory {
    public ItemVoider() {
        super("voider");
        Registry.REGISTRY.put(new ResourceLocation(GravisuitClassic.MODID, "voider"), this);
    }

    @Override
    protected int getEnergyCost(ItemStack itemStack) {
        return 5;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.VOIDER_STORAGE.get();
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.VOIDER_TRANSFER.get();
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        boolean active = nbt.getBoolean("active");
        helper.addSimpleToolTip("item_info.voider_mode", this.translate("item_info." + (active ? "enabled" : "disabled")));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.RIGHT_CLICK, "item_info.voider_toggle"));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (IC2.PLATFORM.isSimulating()) {
            if (IC2.KEYBOARD.isSideInventoryKeyDown(player)){
                IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
                return InteractionResultHolder.success(stack);
            }
            CompoundTag nbt = stack.getOrCreateTag();
            boolean active = nbt.getBoolean("active");
            if (IC2.KEYBOARD.isModeSwitchKeyDown(player)) {
                nbt.putBoolean("active", !active);
                player.displayClientMessage(this.translate("message.voider." + (!active ? "on" : "off")), false);
                return InteractionResultHolder.success(stack);
            }
            return InteractionResultHolder.pass(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return new ItemInventoryVoider(player, this, itemStack, null).load(itemStack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack itemStack, Slot slot) {
        return new ItemInventoryVoider(player, this, itemStack, slot).load(itemStack);
    }

    public List<ItemStack> getModelTypes() {
        List<ItemStack> types = new ObjectArrayList();
        ItemStack inactive = new ItemStack(this);
        ItemStack active = new ItemStack(this);
        active.getOrCreateTag().putBoolean("active", true);
        types.add(inactive);
        types.add(active);
        return types;
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack stack) {
        String suffix = StackUtil.getNbtData(stack).getBoolean("active") ? "_active" : "";
        return IC2Textures.getMappedEntriesItem(GravisuitClassic.MODID, "tools").get("voider" + suffix);
    }

    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity livingEntity) {
        return StackUtil.getNbtData(stack).getBoolean("active") ? 1 : 0;
    }
}
