package trinsdar.gravisuit.items;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.armor.base.ItemArmorElectricJetpackBase;
import ic2.core.item.armor.base.ItemArmorJetpackBase;
import ic2.core.item.armor.base.ItemArmorJetpackBase.IIndirectJetpack;
import ic2.core.item.armor.electric.ItemArmorQuantumSuit;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.util.misc.StackUtil;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.gravisuit.GravisuitClassic;

import java.util.List;
import java.util.Map;

public class ItemArmorGravisuit extends ItemArmorQuantumSuit implements IIndirectJetpack {
    public ItemArmorGravisuit.GravisuitJetpack jetpack = new GravisuitJetpack(this);

    public ItemArmorGravisuit() {
        super(44, EntityEquipmentSlot.CHEST);
        this.setRegistryName("gravisuit");
        this.setUnlocalizedName(GravisuitClassic.MODID + ".gravisuit");
        this.maxCharge = 5000000;
        this.transferLimit = 5000;
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    public String getTexture() {
        return "ic2:textures/models/armor/quantumjetpack";
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
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
    }

    public ItemArmorJetpackBase getJetpack() {
        return this.jetpack;
    }

    public static class GravisuitJetpack extends ItemArmorElectricJetpackBase {
        Item item;

        public GravisuitJetpack(Item owner) {
            super(-1, EntityEquipmentSlot.CHEST);
            this.item = owner;
        }

        @Override
        public boolean canDoRocketMode(ItemStack itemStack) {
            return false;
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
            return 2.5F;
        }

        @Override
        public float getThruster(ItemStack itemStack, HoverMode hoverMode) {
            return 3.5F;
        }

        @Override
        public float getDropPercentage(ItemStack itemStack) {
            return 0;
        }

        @Override
        public int getMaxHeight(ItemStack itemStack, int worldheight) {
            return (int)((float)worldheight * 1.171875f);
        }

        @Override
        public int getMaxRocketCharge(ItemStack itemStack) {
            return 0;
        }

        @Override
        public int getFuelCost(ItemStack itemStack, HoverMode hoverMode) {
            return 30;
        }

        @Override
        public void useEnergy(EntityPlayer player, ItemStack stack, int amount) {
            ElectricItem.manager.use(stack, (double)amount, player);
        }

        @Override
        public String getTexture() {
            return "";
        }

        @Override
        public boolean canProvideEnergy(ItemStack itemStack) {
            return false;
        }

        @Override
        public double getMaxCharge(ItemStack stack) {
            return ElectricItem.manager.getMaxCharge(stack);
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
        public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
            boolean disabled = nbt.getBoolean("disabled");
            PlayerHandler handler = PlayerHandler.getHandlerForPlayer(player);
            byte jetpackTicker = nbt.getByte("JetpackTicker");
            Entity entity = player.getLowestRidingEntity();
            boolean server = IC2.platform.isSimulating();
            if (disabled) {
                if (server) {
                    if (jetpackTicker > 0) {
                        --jetpackTicker;
                        nbt.setByte("JetpackTicker", jetpackTicker);
                    } else if (handler.toggleKeyDown) {
                        nbt.setByte("JetpackTicker", (byte)10);
                        nbt.setBoolean("disabled", false);
                        IC2.platform.messagePlayer(player, Ic2InfoLang.jetpackOn);
                    }
                }

            } else {
                if (handler.toggleKeyDown && jetpackTicker <= 0) {
                    if (server) {
                        nbt.setBoolean("disabled", true);
                        nbt.setByte("JetpackTicker", (byte)10);
                        IC2.platform.messagePlayer(player, Ic2InfoLang.jetpackOff);
                        return;
                    }
                } else if (jetpackTicker > 0) {
                    --jetpackTicker;
                    nbt.setByte("JetpackTicker", jetpackTicker);
                }
            }

            if (!disabled && ElectricItem.manager.getCharge(stack) >= 512 && player.hasItemInSlot(EntityEquipmentSlot.CHEST)){
                this.useEnergy(player, stack, 512);
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
                player.capabilities.allowFlying = false;
                player.capabilities.isFlying = false;
            }
        }
    }
}
