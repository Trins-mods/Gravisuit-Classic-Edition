package trinsdar.gravisuit.items.armor;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.inventory.base.IHandHeldInventory;
import ic2.core.inventory.base.IHasGui;
import ic2.core.item.armor.base.ItemArmorJetpackBase;
import ic2.core.item.armor.electric.ItemArmorElectricJetpack;
import ic2.core.item.armor.electric.ItemArmorQuantumSuit;
import ic2.core.item.inv.inventories.NuclearJetpackInventory;
import ic2.core.item.inv.logics.NuclearJetpackLogic;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;
import trinsdar.ic2c_extras.util.INuclearJetpackLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Optional.Interface(iface = "trinsdar.ic2c_extras.util.INuclearJetpackLogic", modid = "ic2c_extras")
public class ItemArmorNuclearGravisuit extends ItemArmorQuantumSuit implements ItemArmorJetpackBase.IIndirectJetpack, IHandHeldInventory, INuclearJetpackLogic {
    GravisuitJetpack jetpack = new GravisuitJetpack(this);

    public ItemArmorNuclearGravisuit() {
        super(44, EntityEquipmentSlot.CHEST);
        this.setRegistryName("nucleargravisuit");
        this.setTranslationKey(GravisuitLang.nuclearGravisuit);
        this.maxCharge = GravisuitConfig.powerValues.nuclearGravisuitStorage;
        this.transferLimit = GravisuitConfig.powerValues.nuclearGravisuitTransfer;
        this.setCreativeTab(IC2.tabIC2);
        if (GravisuitConfig.enableGravisuitTier4){
            this.tier = 4;
        }else {
            this.tier = 3;
        }
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
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("gravisuit_items")[22];
    }

    @Override
    public String getTexture() {
        return "gravisuit:textures/models/nuclear_gravisuit";
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (playerIn.isSneaking()) {
            if (IC2.platform.isSimulating()) {
                IC2.platform.launchGui(playerIn, this.getInventory(playerIn, hand, playerIn.getHeldItem(hand)), hand);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
        } else {
            return super.onItemRightClick(worldIn, playerIn, hand);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        super.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
        this.jetpack.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        this.jetpack.onArmorTick(world, player, stack);
        this.jetpack.onReactorTick(world, player, stack);
    }

    public static boolean hasGravisuit(EntityPlayer player){
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        return !chest.isEmpty() && chest.getItem() instanceof ItemArmorGravisuit;
    }
    public static boolean hasQuantumLegs(EntityPlayer player){
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

        return !legs.isEmpty() && legs.getItem() instanceof ItemArmorQuantumSuit;
    }

    public ItemArmorJetpackBase getJetpack() {
        return this.jetpack;
    }

    @Override
    public IHasGui getInventory(EntityPlayer player, EnumHand hand, ItemStack stack) {
        return new NuclearJetpackInventory(player, this, stack);
    }

    @Override
    public int getGuiId(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getNbtData(stack);
        return nbt.hasKey("GuiID") ? nbt.getInteger("GuiID") : -1;
    }

    @Override
    public void setGuiID(ItemStack stack, int id) {
        if (id == -1) {
            StackUtil.getOrCreateNbtData(stack).removeTag("GuiID");
        } else {
            StackUtil.getOrCreateNbtData(stack).setInteger("GuiID", id);
        }
    }

    @Optional.Method(modid = "ic2c_extras")
    @Override
    public NuclearJetpackLogic getLogic(EntityPlayer entityPlayer, ItemStack itemStack) {
        return jetpack.getLogic(entityPlayer, itemStack);
    }

    public static class GravisuitJetpack extends ItemArmorElectricJetpack {
        Item item;

        public GravisuitJetpack(Item owner) {
            super();
            this.item = owner;
        }


        @Override
        @SideOnly(Side.CLIENT)
        public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
            List<String> ctrlTip = sortedTooltip.get(ToolTipType.Ctrl);
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(GravisuitLang.gravisuitToggleCombo.getLocalizedFormatted(IC2.keyboard.getKeyName(5), IC2.keyboard.getKeyName(1)), GravisuitLang.graviEngineToggle));
            ctrlTip.add(TextFormatting.UNDERLINE + GravisuitLang.graviEngineOnInfo.getLocalized());
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(GravisuitLang.doubleJump.getLocalizedFormatted(IC2.keyboard.getKeyName(6)), GravisuitLang.creativeFly));
            ctrlTip.add(TextFormatting.UNDERLINE + GravisuitLang.graviEngineOffInfo.getLocalized());
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(6), Ic2InfoLang.jetpackJumpToFly));
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(2), Ic2InfoLang.jetpackModeSwitch));
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(0), Ic2InfoLang.jetpackQuickToggle));
            ctrlTip.add(TextFormatting.UNDERLINE + Ic2Lang.pressTo.getLocalizedFormatted(IC2.keyboard.getKeyName(5), Ic2InfoLang.jetpackToggle));
        }

        @Override
        public boolean isElectricJetpack(ItemStack itemStack) {
            return true;
        }

        @Override
        public boolean canDoAdvHoverMode(ItemStack stack) {
            return true;
        }

        @Override
        public int getMaxHeight(ItemStack itemStack, int worldheight) {
            return (int)((float)worldheight * 1.171875f);
        }

        @Override
        public String getTexture() {
            return "";
        }

        @Override
        public int getTier(ItemStack itemStack) {
            return 3;
        }

        @Override
        public double getTransferLimit(ItemStack itemStack) {
            return 0;
        }

        @Override
        public int getJetpackUse(ItemStack stack) {
            NBTTagCompound nbt = StackUtil.getNbtData(stack);
            return nbt.getBoolean("enabled") ? 2 : nbt.getInteger("LastUseMode");
        }

        @Override
        public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            boolean enabled = nbt.getBoolean("enabled");
            PlayerHandler handler = PlayerHandler.getHandlerForPlayer(player);
            byte jetpackTicker = nbt.getByte("JetpackTicker");
            boolean server = IC2.platform.isSimulating();
            if (enabled) {
                if (server) {
                    if (jetpackTicker > 0) {
                        --jetpackTicker;
                        nbt.setByte("JetpackTicker", jetpackTicker);
                    } else if (handler.toggleKeyDown && handler.boostKeyDown) {
                        nbt.setByte("JetpackTicker", (byte)10);
                        nbt.setBoolean("enabled", false);
                        IC2.platform.messagePlayer(player, GravisuitLang.graviEngineOff);
                    }
                }

            } else {
                if (handler.toggleKeyDown && handler.boostKeyDown && jetpackTicker <= 0) {
                    if (server) {
                        nbt.setBoolean("enabled", true);
                        nbt.setByte("JetpackTicker", (byte)10);
                        IC2.platform.messagePlayer(player, GravisuitLang.graviEngineOn);
                        return;
                    }
                } else if (jetpackTicker > 0) {
                    --jetpackTicker;
                    nbt.setByte("JetpackTicker", jetpackTicker);
                }
            }

            if (enabled){
                if (ElectricItem.manager.getCharge(stack) >= 1024){
                    if (!player.capabilities.isCreativeMode && !player.isSpectator()){
                        if (handler.quantumArmorBoostSprint && player.isSprinting() && ItemArmorGravisuit.hasQuantumLegs(player)){
                            this.useEnergy(player, stack, 1024);
                        }else {
                            this.useEnergy(player, stack, 512);
                        }
                    }
                    player.capabilities.allowFlying = true;
                    player.stepHeight = 1.0625F;
                    boolean flying = player.capabilities.isFlying;
                    if(flying){
                        boolean sneaking = player.isSneaking();

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
                    }
                }else {
                    if (!player.capabilities.isCreativeMode && !player.isSpectator()){
                        player.stepHeight = 0.6F;
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                    }
                }
            }else {
                if (!player.capabilities.isCreativeMode && !player.isSpectator()){
                    player.stepHeight = 0.6F;
                    player.capabilities.allowFlying = false;
                    player.capabilities.isFlying = false;
                }
                super.onArmorTick(world, player, stack);
            }
        }

        public void onReactorTick(World world, EntityPlayer player, ItemStack stack) {
            NuclearJetpackLogic logic = this.getLogic(player, stack);
            logic.onTick(stack);
            logic.save(stack);
        }

        public NuclearJetpackLogic getLogic(EntityPlayer player, ItemStack stack) {
            return new NuclearJetpackLogic(stack, player, this.armorType);
        }

        public static List<String> playersWithSet = new ArrayList<String>();

        public static String playerKey(EntityPlayer player) {
            return player.getGameProfile().getName() + ":" + player.getEntityWorld().isRemote;
        }

        @SubscribeEvent
        public void updatePlayerAbilityStatus(LivingEvent.LivingUpdateEvent event) {
            if (event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                String key = playerKey(player);
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
                }
            }
        }
    }
}
