package trinsdar.gravisuit.items.container;

import ic2.core.inventory.base.IC2ItemInventory;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.gui.GuiComponentContainer;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import trinsdar.gravisuit.items.tools.ItemRelocator;

public class ItemInventoryRelocator extends IC2ItemInventory {
    ItemStack relocator;
    public ItemInventoryRelocator(EntityPlayer player, ItemRelocator inv, ItemStack relocator) {
        super(player, inv, relocator);
        this.relocator = relocator;
    }

    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public ContainerIC2 getGuiContainer(EntityPlayer player) {
        NBTTagCompound nbt = StackUtil.getNbtData(relocator);
        if (player.isSneaking() && nbt.getByte("TeleportMode") == 0 && nbt.getBoolean("lookingAtBlock")){
            nbt.removeTag("lookingAtBlock");
            return new ItemContainerRelocatorAdd(this, this.getID(), relocator, player);
        }
        return new ItemContainerRelocatorDisplay(this, this.getID(), relocator, player);
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
