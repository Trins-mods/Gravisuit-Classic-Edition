/*
package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IC2ItemInventory;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import trinsdar.gravisuit.items.tools.ItemRelocator;

public class ItemInventoryRelocator extends IC2ItemInventory {
    ItemStack relocator;
    EnumHand hand;
    public ItemInventoryRelocator(EntityPlayer player, ItemRelocator inv, ItemStack relocator, EnumHand hand) {
        super(player, inv, relocator);
        this.relocator = relocator;
        this.hand = hand;
    }

    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public ContainerIC2 getGuiContainer(EntityPlayer player) {
        NBTTagCompound nbt = StackUtil.getNbtData(relocator);
        if (player.isSneaking() && nbt.getByte("TeleportMode") == 0){
            return new ItemContainerRelocatorAdd(this, this.getID(), hand, player);
        }
        return new ItemContainerRelocatorDisplay(this, this.getID(), hand, player);
    }

    @Override
    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return GuiRelocator.class;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    @Override
    public boolean hasGui(EntityPlayer entityPlayer) {
        return true;
    }
}
*/
